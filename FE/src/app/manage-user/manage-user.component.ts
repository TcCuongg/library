import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {Account} from "../app.module";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.css']
})
export class ManageUserComponent implements OnInit {
  datasAccount:Account[]=[];
  datasCheckAccuont:Account[]=[];
  account: Account | undefined;
  count = 0;
  inputValue: string = '';
  inputName:string = '';
  inputEmail = '';
  inputAddress = '';
  inputLevel = '';
  inputStatus = '';
  inputPhone = ''

  valueSave:any;


  admin:Account | undefined;

  logOut = false;

  constructor(private productService: ProductService) {}
  ngOnInit() {
    this.getAll(this.count, 9);
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
  }
  getAll(count:number, step:number){
    this.productService.getAllAccount(count, step).subscribe((res:any)=>{
      this.datasAccount = res
    })
  }

  clickAccount(account:Account){
    this.account = account;
    this.inputName = this.account.name ?? '';
    this.inputEmail = this.account.email ?? '';
    this.inputPhone = (this.account.phone ?? 0).toString();
    this.inputAddress = this.account.address ?? '';
    this.inputLevel = (this.account.level ?? 0).toString();
    this.inputStatus = this.account.status ?? '';
  }

  changeAccount(){
    const url = 'http://localhost:8080/account/updateAccount';
    const body = {cardNumber: this.account?.cardNumber, name: this.inputName, email: this.inputEmail, phone: this.inputPhone, address: this.inputAddress, level: this.inputLevel, status: this.inputStatus}
    this.productService.postData(url, body).subscribe((res:any)=>{
      this.account = res;
    });
    this.getAll(this.count, 9);
  }

  clickSearch(){
    this.count = 0;
    this.productService.getSearchAccount(this.inputValue, this.count, 9).subscribe((res:any)=>{
      this.datasAccount = res
    })
  }
  clickMore(){
    if (this.datasAccount.length==9){
      this.count = this.count + 1;
      if(this.inputValue == ''){
        this.productService.getAllAccount(this.count, 9).subscribe((res:any)=>{
          this.datasCheckAccuont = res
        })
      }
      else {
        this.productService.getSearchAccount(this.inputValue, this.count, 9).subscribe((res:any)=>{
          this.datasAccount = res
        })
      }
      if(this.datasCheckAccuont.length != 0){
        this.datasAccount = this.datasCheckAccuont;
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      if(this.inputValue == ''){
        this.getAll(this.count, 9);
      }
      else {
        this.productService.getSearchAccount(this.inputValue, this.count, 9).subscribe((res:any)=>{
          this.datasAccount = res
        })
      }
    }
  }
  changeLogout(){
    return{
      'display': this.logOut ? 'block' : 'none'
    }
  }

  clickUser(){
    this.logOut = !this.logOut;
  }

  clickLogout(){
    this.valueSave.clear();
  }
  closeAccount(){
    this.count = 0;
    const url = 'http://localhost:8080/account/closeAccountLowLevel';
    const body = '';
    this.productService.putData(url, body).subscribe((res:any)=>{
      this.datasAccount = res;
      for(let item of this.datasAccount){
        const url = 'http://localhost:8080/notification/addNewNotification';
        const body = {mainContentId: 4, accountId: item.cardNumber};
        this.productService.postData(url, body).subscribe();
      }
    });
  }
  clickManageUser(){
    this.count = 0;
    this.getAll(this.count, 9);
  }
}
