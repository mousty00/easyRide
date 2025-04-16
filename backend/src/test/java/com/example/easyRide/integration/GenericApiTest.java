package com.example.easyRide.integration;

import com.example.easyRide.dto.info.MessageBodyInfo;
import io.restassured.path.json.JsonPath;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe base per i test REST API con metodi generalizzati
 */
public abstract class GenericApiTest<T, ID> extends BaseIntegrationTest {

    protected abstract String getBasePath();
    protected abstract List<T> getExpectedEntities();
    protected abstract T createNewEntity();
    protected abstract T modifyEntity(T entity);
    protected abstract ID getEntityId(T entity);
    protected abstract Class<T> getEntityClass();

    @Test
    @DisplayName("get all entities")
    public void getAllEntities_Success() {
        JsonPath jsonPath = given()
                .port(super.port)
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization", "Bearer %s".formatted(jwtToken))
                .get(getBasePath())
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        List<T> entities = jsonPath.getList("results", getEntityClass());

        assertThat(entities)
                .usingRecursiveComparison()
                .isEqualTo(getExpectedEntities());
    }

    @Test
    @DisplayName("get entity by id")
    public void getEntityById_Success() {
        T expectedEntity = getExpectedEntities().getFirst();
        ID entityId = getEntityId(expectedEntity);

        T actualEntity = given()
                .port(super.port)
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization", "Bearer %s".formatted(jwtToken))
                .get("%s/{id}".formatted(getBasePath()), entityId)
                .then()
                .statusCode(200)
                .extract()
                .as(getEntityClass());

        assertThat(actualEntity).isEqualTo(expectedEntity);
    }

    @Test
    @DisplayName("add new entity")
    public void addEntity_Success() {
        T newEntity = createNewEntity();

        MessageBodyInfo messageBodyInfo = given()
                .port(super.port)
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization", "Bearer %s".formatted(jwtToken))
                .body(newEntity)
                .post("%s/create".formatted(getBasePath()))
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .as(MessageBodyInfo.class);

        assertThat(messageBodyInfo.message()).isEqualTo("%s saved".formatted(getEntityName()));
    }

    @Test
    @DisplayName("update existing entity")
    public void updateEntity_Success() {
        T entityToUpdate = getExpectedEntities().getFirst();
        T entityUpdated = modifyEntity(entityToUpdate);

        MessageBodyInfo messageBodyInfo = given()
                .port(super.port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .header("Authorization", "Bearer %s".formatted(jwtToken))
                .body(entityUpdated)
                .put("%s/update".formatted(getBasePath()))
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .as(MessageBodyInfo.class);

        assertThat(messageBodyInfo.message()).isEqualTo("%s updated".formatted(getEntityName()));
    }

    @Test
    @DisplayName("delete entity")
    public void deleteEntity_Success() {
        T entityToDelete = getExpectedEntities().getFirst();
        ID id = getEntityId(entityToDelete);

        MessageBodyInfo messageBodyInfo = given()
                .port(super.port)
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization", "Bearer %s".formatted(jwtToken))
                .pathParam("id", id)
                .delete("%s/delete/{id}".formatted(getBasePath()))
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .as(MessageBodyInfo.class);

        assertThat(messageBodyInfo.message())
                .isEqualTo("%s with id %%s deleted".formatted(getEntityName()), id);
    }

    protected String getEntityName() {
        String className = getEntityClass().getSimpleName();
        return className.endsWith("DTO") ? className.substring(0, className.length() - 3) : className;
    }
}