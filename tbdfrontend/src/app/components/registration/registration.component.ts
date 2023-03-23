import { Component, OnInit } from '@angular/core';
import { DataService } from '../../data.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  public imageURL : any;
  registrationForm !:FormGroup;
  submitted = false;
  genders = ["Male", "Female", "Other"]
  provinces = ["AB", "BC", "MB", "NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK", "YT"]
  constructor(private dataService: DataService, private formBuilder:FormBuilder, private sanitizer:DomSanitizer) { }
  ngOnInit(): void {
    this.registrationForm=this.formBuilder.group({
      userName:["",Validators.required],
      firstName:["",Validators.required],
      middleName:[""],
      lastName:["",Validators.required],
      dateOfBirth:["",Validators.required],
      email:["",[Validators.required,Validators.email]],
      password:["",[Validators.required,Validators.minLength(8)]],
      countryCode:["",Validators.required],
      mobileNumber:["",[Validators.minLength(10),Validators.required]],
      streetNumber:["",Validators.required],
      gender:["",Validators.required],
      unitNumber:[""],
      streetName:["",Validators.required],
      city:["",Validators.required],
      province:["",Validators.required],
      postalCode:["",Validators.required],
      sinNumber:["",[Validators.required,Validators.minLength(10)]]
    })
  }
  get rfc(){
    return this.registrationForm.controls;
  }
  onSubmit(){
    this.submitted=true;
    if(this.registrationForm.invalid){
      return;
    }
    this.registrationForm.value['gender']=this.registrationForm.value['gender'].toUpperCase()
    this.dataService.sendRegistrationDetails(this.registrationForm.value).subscribe(data => {
      if (data.success) {
        alert("Registration Successful")
        let imgBytes = data.pdf;
        let byteCharacters = atob(imgBytes);
        let byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        let byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: 'image/png' });
        console.log(URL.createObjectURL(blob));
        this.imageURL = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob));
      } else {
        alert("Registration UnSuccessful")
      }
    })
  }
  onReset(){
    this.submitted=false;
    this.registrationForm.reset();
  }
}
