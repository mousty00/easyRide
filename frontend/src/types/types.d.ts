export type Link = {
  label: string;
  path: string;
  icon: string;
  active: boolean;
  disabled: boolean;
  target: string;
  hidden: boolean;
};

export type formItem = {
  id?: string;
  label: string;
  type: string;
  input: boolean;
  icon?: string;
};

export type Ride = {
  id: number;
  state: string;
  distance: number;
  startDate: Date;
  endDate: Date;
};

export type User = {
  id?: number;
  firstName?: string;
  lastName?: string;
  birthDate?: string;
  age?: number;
  taxIdCode?: string;
  registrationDate?: Date;
  rides?: number[];
  rideList?: Ride[];
};

export type LoginRequest = {
  firstName: string;
  lastName: string;
};

export type Response = {
  token?: string;
  message?: string;
};

export type Driver = {
  id: number;
  firstName: string;
  lastName: string;
  age: number;
  birthDate: string;
  taxIdCode: string;
  rides: number[];
  vehicles: number[];
};

export type Vehicle = {
  id: number;
  plateNumber: string;
  model: string;
  color: string;
  idDriver: number;
}

export type responseMessage = {
  message: string;
}

export type bodyUserRequest = {
  id?: number;
  firstName?: string;
  lastName?: string;
  birthDate?: string;
  taxIdCode?: string;
  registrationDate?: Date;
  rides?: number[];
}

export type userValidation = {
  isValid: boolean;
  error: userError
}

export type userError = {
  id?: number;
  firstName?: string;
  lastName?: string;
  birthDate?: string;
  taxIdCode?: string;
  registrationDate?: Date;
  rides?: number[];
}

export type customInput = {
  id: string,
  label: string,
  value: string | Date,
  type: string,
  disabled: boolean,
  valueChange: ($event:string | Date, value: string ) => void,
  hasError: (fieldName: string) => boolean,
  error: string
}
