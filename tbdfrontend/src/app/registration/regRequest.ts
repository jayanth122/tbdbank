import { DatePipe } from "@angular/common"
import { EmailValidator } from "@angular/forms";

export interface RegRequest {
    firstName: string,
    middleName: string,
    lastName: string,
    gender: string,
    userName: string,
    password: string,
    confirmPassword: string,
    dateOfBirth: DatePipe,
    sinNumber: number,
    streetNumber: number,
    unitNumber: number,
    streetName: string,
    city: string,
    province: string,
    postalCode: string,
    email: EmailValidator,
    countryCode: number,
    mobileNumber: number
}