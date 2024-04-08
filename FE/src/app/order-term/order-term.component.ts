import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PlatformLocation} from "@angular/common";
import {ProductService} from "../product.service";
import {Account, Book, Category} from "../app.module";


@Component({
  selector: 'app-order-term',
  templateUrl: './order-term.component.html',
  styleUrls: ['./order-term.component.css']
})
export class OrderTermComponent implements OnInit{


  data: any;
  count= 0;
  datasBook:Book[]=[];
  dataAccount:Account | undefined;
  inputValue: string = '';
  datasCategory:Category[]=[];
  mess = '';
  buyNew:Book[]=[];
  buyDelivered:Book[]=[];
  buyDelivering:Book[]=[];

  userName = '';
  userEmail = '';
  userPhone = '';
  userAddress = '';
  passOld = '';
  passNew = '';
  mess1 = '';

  valueSave:any;


  isMess = false;
  isSetUser = false;
  isClickMore = false;
  isClickLogin = false;
  isLogin1 = true;
  isLogin2 = false;
  logOut = false;
  isBuy = false;
  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.data = params;
    });
    this.mess += this.data.message;
    this.getAllCategory();
    this.valueSave = window.localStorage;
    this.dataAccount = JSON.parse(this.valueSave.getItem('value'));
    this.checkUser();
    this.checkFontEnd();
    this.getBooks(this.count);
  }

  checkFontEnd(){
    if(this.dataAccount?.type == 'admin'){
      this.valueSave.clear();
      this.valueSave.setItem('value', JSON.stringify(this.dataAccount))
      let data = { message: this.dataAccount.name};
      this.router.navigate(['/manageUser', data]);
    }
  }
  checkUser(){
    if(this.dataAccount != undefined){
      this.isLogin1 = !this.isLogin1;
      this.isLogin2 = !this.isLogin2;
      this.isBuy = !this.isBuy;
    }
  }
  getBooks(count:number){
    this.productService.getAllBookByAccountBuyNew(this.dataAccount?.cardNumber ?? 0, this.count, 4).subscribe((res:any)=>{
      this.buyNew = res;
      if(this.buyNew.length != 0) this.isBuyNew = true;
    });
    this.productService.getAllBookByAccountBuyDelivered(this.dataAccount?.cardNumber ?? 0, this.count, 4).subscribe((res:any)=>{
      this.buyDelivered = res;
      if(this.buyDelivered.length != 0) this.isBuyDelivered = true;
    });
    this.productService.getAllBookByAccountBuyDelivering(this.dataAccount?.cardNumber ?? 0, this.count, 4).subscribe((res:any)=>{
      this.buyDelivering = res;
      if(this.buyDelivering.length > 0) this.isBuyDelivering = true;
    });
  }
  isBuyNew = false;
  isBuyDelivered = false;
  isBuyDelivering = false

  changeBuyNew(){
    return{
      'display': this.isBuyNew ? 'grid':'none',
    }
  }
  changeBuyDelivered(){
    return{
      'display': this.isBuyDelivered ? 'grid':'none',
    }
  }
  changeBuyDelivering(){
    return{
      'display': this.isBuyDelivering ? 'grid':'none',
    }
  }

  changeNav(){
    let listStatus = [this.isBuyNew, this.isBuyDelivered, this.isBuyDelivering]
    let size = listStatus.filter(item => item).length;
    if(size == 1){
      return{'height':'30rem',}
    }
    else if(size == 2){
      return {'height':'58rem',}
    }
    else return {'height':'86rem',}
  }
  clickMoreCategory(){
    this.isClickMore = !this.isClickMore;
  }
  changeMore(){
    return{
      'display':this.isClickMore ? 'block' : 'none'
    };
  }
  newListBook(){
    this.count = 0;
    this.mess = this.inputValue;
    this.productService.getBookByTitle(this.inputValue, this.count, 8).subscribe((res:any)=>{
      this.datasBook = res
    })
  }

  sendBookContent(book:Book){
    let data = { message: book.id};
    this.router.navigate(['/Buy', data]);
  }

  getAllCategory(){
    this.productService.getAllCategory().subscribe((res:any)=>{
      this.datasCategory = res
    })
  }
  value='';
  clickCategory(category:Category){
    this.mess = category.name ?? '';
    this.count = 0;
    this.getBooks(this.count);
  }
  clickLogin(){
    this.isClickLogin = !this.isClickLogin;
  }
  sendByCategory(category:Category){
    let data = { message: category.name};
    this.router.navigate(['/moreBook', data]);
  }
  sendBookMore(string:string){
    let data = { message: string};
    this.router.navigate(['/moreBook', data]);
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
  clickCart(){
    if(this.dataAccount?.cardNumber != undefined){
      this.count = 0;
      this.productService.getAllBookByAccountCart(this.dataAccount.cardNumber, this.count, 4).subscribe((res:any)=>{
        this.datasBook = res
      });
    }
    else {
      this.count = 0;
      this.mess = 'Giỏ hàng';
      this.clickLogin();
    }
  }
  setUser(){
    let userName = this.dataAccount?.name;
    let userEmail = this.dataAccount?.email;
    let userPhone = this.dataAccount?.phone?.toString();
    let userAddress = this.dataAccount?.address;
    let account:Account;
    if(this.userName != '') userName = this.userName;
    if(this.userEmail != '') userEmail = this.userEmail;
    if(this.userPhone != '') userPhone = this.userPhone;
    if(this.userAddress != '') userAddress = this.userAddress;

    if(this.userName + this.userEmail + this.userPhone + this.userAddress + this.passOld + this.passNew == '') this.mess1 = 'Vui lòng nhập thông tin để thay đổi';
    else if(this.passOld != '' && this.passNew == '') this.mess1 = 'Đổi mật khẩu thiếu mật khẩu mới';
    else if(this.passOld == '' && this.passNew != '') this.mess = 'Đổi mật khẩu phải nhập mật khẩu cũ';
    else{
      const body = {userId: this.dataAccount?.cardNumber, userName:userName, userEmail:userEmail, userPhone:userPhone
        , userAddress:userAddress, passOld:this.passOld, passNew:this.passNew};
      this.productService.postData('http://localhost:8080/account/setUser', body).subscribe((res:any) => {
        account = res;
        if(account.address != null){
          this.valueSave.clear();
          this.valueSave.setItem('value', JSON.stringify(account));
          this.dataAccount = JSON.parse(this.valueSave.getItem('value'));
          this.mess1 = 'Sửa tài khoản thành công';
          this.isSetUser = false;
        }
        else this.mess1 = 'Sửa tài khoản không thành công vui lòng kiểm tra lại các trường';
      })
    }
    this.isMess = true;
  }
  clickCloseMess(){
    this.isMess = false;
    this.mess1 = '';
  }
  changeMess(){
    return{
      'display':this.isMess ? 'block':'none',
    }
  }
  clickSetting(){
    this.isSetUser = false;
    this.userName = '';
    this.userEmail = '';
    this.userPhone = '';
    this.userAddress = '';
    this.passOld = '';
    this.passNew = '';
  }
  changeSetUser(){
    return{
      'display':this.isSetUser ? 'block':'none',
    }
  }
}

