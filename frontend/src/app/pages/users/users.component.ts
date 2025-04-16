import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {DataService} from 'src/app/service/data/data.service';
import {User} from 'src/types/types';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  filteredUsers: User[] = [];
  errorMessage: string = '';
  totalPages: number[] = [];
  selectedPage: number = 0;
  pageIndex: number = 0;

  constructor(private dataService: DataService, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.getUsers(this.pageIndex);
    this.filteredUsers = this.users;
  }

  editUser(user?: User) {
    console.log(user);
  }

  getUsers(pageIndex?: number | undefined) {
    if (pageIndex !== undefined) {
      this.selectedPage = pageIndex;
    }
    this.dataService.getAll<User>(
      'users',
      this.totalPages,
      this.users,
      this.errorMessage,
      () => {
        this.users.forEach(
          (user) => (user.age = this.dataService.age(user.birthDate!))
        );
      },
      this.selectedPage,
      pageIndex
    );
    this.filteredUsers = this.users;


  }

  deleteUser(user?: User) {
    this.dataService.delete(
      `users/delete/${user?.id}`,
      user?.firstName,
      this.getUsers.bind(this)
    );
  }
}
