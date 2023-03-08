import {Form, FormGroup} from "@angular/forms"

export function PasswordChecker(
  controlName:string,
  CompareControlName:string
){
  return (formGroup : FormGroup) =>{
    const pass = formGroup.controls[controlName];
    const confPass = formGroup.controls[CompareControlName];
    if(pass.value !== confPass.value ){
      confPass.setErrors({mustmatch:true})
    }else{
      confPass.setErrors(null);
    }
  }
}
