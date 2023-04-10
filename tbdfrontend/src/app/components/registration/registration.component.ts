import { Component, OnInit } from '@angular/core';
import { DataService } from '../../data.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { DomSanitizer } from '@angular/platform-browser';
import {Router} from "@angular/router";
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
  countryCode = ["1","91","44","52","86"]
  testAccount = ["Yes","No"]
  mapYesNoToBoolean(value: string): boolean {
    return value === 'Yes' ? true : false;
  }
  constructor(private dataService: DataService, private formBuilder:FormBuilder, private router:Router) { }
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
      sinNumber:["",[Validators.required,Validators.minLength(10)]],
      testAccount:["",Validators.required]
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
    this.registrationForm.value['testAccount'] = this.mapYesNoToBoolean(this.registrationForm.value['testAccount']);
    this.dataService.sendRegistrationDetails(this.registrationForm.value).subscribe(data => {
      if (data.success) {
        alert(data.message)
        this.dataService.setVerificationImage(data.qrImage)
        this.dataService.setVerificationPdf(data.qrPdf)
        this.router.navigate(['verification'])
      } else {
        alert(data.message)
      }
    },error => {
      console.error(error)
    })
  }
  onReset(){
    this.submitted=false;
    this.registrationForm.reset();
  }
}
