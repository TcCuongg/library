import {Component, OnInit} from '@angular/core';
import {Account, Book, Category} from "../app.module";
import {ProductService} from "../product.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit  {
  datasAll:Book[]=[];
  datasDesc:Book[]=[];
  dataAccount:Account | undefined;
  datasCategory:Category[]=[];
  top:Book | undefined;
  value:string | undefined;
  inputValue: string = '';
  inputValuePass = '';
  inputValueEmail = '';

  inputValueName = '';
  inputValueAddEmail = '';
  inputValuePhone = '';
  inputValueAddress = '';
  inputValueAddPass = '';

  valueSave:any;

  isClickLogin = false;
  isClickMore = false;
  isClickNewUser = false;
  isLogin1 = true;
  isLogin2 = false;
  logOut = false;
  constructor(private productService: ProductService,
              private router: Router) { }

  ngOnInit(): void {
    this.getAllBook(0, 4);
    this.getTopBook();
    this.getBookFollowDesc(0, 4);
    this.getAllCategory();
    this.valueSave = window.localStorage;
    this.dataAccount = JSON.parse(this.valueSave.getItem('value'));
    this.checkUser();
    this.checkFontEnd();
  }
  checkUser(){
    if(this.dataAccount != undefined){
      this.isLogin1 = !this.isLogin1;
      this.isLogin2 = !this.isLogin2;
    }
  }
  getAllBook(count:number, step:number){
    this.productService.getAllBook(count, step).subscribe((res:any)=>{
      this.datasAll = res
    })
  }
  getTopBook(){
    this.productService.getTopBook().subscribe((res:any)=>{
      this.top = res
    })
  }
  getBookFollowDesc(count:number, step:number){
    this.productService.getBookFollowDesc(count, step).subscribe((res:any)=>{
      this.datasDesc = res
    })
  }
  getAllCategory(){
    this.productService.getAllCategory().subscribe((res:any)=>{
      this.datasCategory = res
    })
  }
  clickLogin(){
    this.isClickLogin = !this.isClickLogin;
  }
  changeLogin(){
    return{
      'display':this.isClickLogin ? 'block' : 'none'
    };
  }
  clickMore(){
    this.isClickMore = !this.isClickMore;
  }
  changeMore(){
    return{
      'display':this.isClickMore ? 'block' : 'none'
    };
  }
  send(value:string) {
    if(value == "Giỏ hàng"){
      if(this.dataAccount != undefined){
        let data = { message: value, cardNumber: this.dataAccount.cardNumber};
        this.router.navigate(['/moreBook', data]);
      }
      else this.clickLogin();
    }
    else {
      let data = { message: value};
      this.router.navigate(['/moreBook', data]);
    }
  }
  sendInputValue(){
    let data = { message: this.inputValue };
    this.router.navigate(['/moreBook', data]);
  }

  sendByCategory(category:Category){
    let data = { message: category.name};
    this.router.navigate(['/moreBook', data]);
  }

  sendToBuy(book:Book){
    if(this.dataAccount != undefined){
      let data = { message: book.id};
      this.router.navigate(['/Buy', data]);
    }
    else {
      this.clickLogin();
    }
  }

  sendBookContent(name:Book){
    let data = { message: name.id};
    this.router.navigate(['/Search', data]);
  }

  clickNewUser(){
    this.isClickNewUser = !this.isClickNewUser;
  }

  changeNewUser(){
    return{
      'display':this.isClickNewUser ? 'block' : 'none'
    }
  }

  checkFontEnd(){
    if(this.dataAccount?.type == 'admin'){
      this.valueSave.clear();
      this.valueSave.setItem('value', JSON.stringify(this.dataAccount))
      let data = { message: this.dataAccount.name};
      this.router.navigate(['/manageUser', data]);
    }
  }

  clickLoginPost(){
    if(this.inputValuePass != '' && this.inputValueEmail != ''){
      const url = 'http://localhost:8080/account/Login';
      const data = { email: this.inputValueEmail, passWord: this.inputValuePass};

      this.productService.postData(url, data).subscribe((res:any)=>{
        this.dataAccount = res
      });
      if(this.dataAccount?.type == 'user' && this.dataAccount.status != 'close'){
        this.isClickLogin = !this.isClickLogin;
        this.checkUser();
        this.valueSave.clear();
        this.valueSave.setItem('value', JSON.stringify(this.dataAccount))
      }
      else if(this.dataAccount?.type == 'admin' && this.dataAccount.status != 'close'){
        this.valueSave.clear();
        this.valueSave.setItem('value', JSON.stringify(this.dataAccount))
        let data = { message: this.dataAccount.name};
        this.router.navigate(['/manageUser', data]);
      }
    }
  }
  clickSaveCart(book:Book){
    if(this.dataAccount != undefined){
      const url = 'http://localhost:8080/cart/addNewCart';
      const data = { bookStorageId: book.id, accountId: this.dataAccount.cardNumber};
      this.productService.postData(url, data).subscribe();
    }
    else this.clickLogin();
  }
  lastLogin1(){
    return{
      'display':this.isLogin1 ? 'block' : 'none'
    }
  }
  lastLogin2(){
    return{
      'display':this.isLogin2 ? 'block' : 'none'
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
    this.checkUser()
    this.valueSave.clear();
    this.dataAccount = undefined;
    this.clickUser();
  }

  clickAddUser(){
    if(this.inputValueName !='' && this.inputValueAddEmail !='' && this.inputValuePhone !='' && this.inputValueAddress !='' && this.inputValueAddPass !=''){
      const url = 'http://localhost:8080/account/addNewAccount';
      const data = { username: this.inputValueName, email: this.inputValueAddEmail, phone: this.inputValuePhone,
        address: this.inputValueAddress, password: this.inputValueAddPass };
      this.productService.postData(url, data).subscribe();
    }
  }
}
