import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Account, Book, Buy, Cart, Category} from "../app.module";
import {ProductService} from "../product.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-buy',
  templateUrl: './buy.component.html',
  styleUrls: ['./buy.component.css']
})
export class BuyComponent implements OnInit{
  protected readonly parseInt = parseInt;
  protected readonly String = String;
  inputValue: string = '1';
  inputValueSearch = '';
  count:number | undefined;
  data: any
  book:Book | undefined;
  datasCategory:Category[]=[];
  datasBook:Book[]=[];
  dataAccount:Account | undefined;


  isSetUser = false;
  isMess = false;
  isClickLogin = false;
  isClickNewUser = false;

  inputValueAddress = '';
  inputValueAddPass = '';
  inputValuePhone = '';
  inputValueName = '';
  inputValueAddEmail = '';
  mess = '';
  userName = '';
  userEmail = '';
  userPhone = '';
  userAddress = '';
  passOld = '';
  passNew = '';
  inputValuePass = '';
  inputValueEmail = '';

  valueSave:any;

  isClickMore = false;
  isLogin1 = true;
  isLogin2 = false;
  logOut = false;
  isClickBuy = false;

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.data = params;
    });
    this.getBookByBookId(this.data.message);
    this.getAllCategory();
    this.valueSave = window.localStorage;
    this.dataAccount = JSON.parse(this.valueSave.getItem('value'));
    this.checkUser();
    this.getBookByStorage();
  }

  getBookByStorage(){
    this.productService.getBookByBookStorageOnStorage(Number(this.data.message), 0, 4).subscribe((res:any) => {
      this.datasBook = res;
    })
  }

  clickBook(book:Book){
    let data = { message: book.id};
    this.router.navigate(['/Buy', data]);
    this.route.params.subscribe(params => {
      this.data = params;
      this.getBookByBookId(this.data.message);
    });
  }

  sendStorage(){
    this.productService.getStorageId(this.data.message).subscribe((res:any) => {
      let data = { storage: res};
      this.router.navigate(['/moreBook', data]);
    })
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

    if(this.userName + this.userEmail + this.userPhone + this.userAddress + this.passOld + this.passNew == '') this.mess = 'Vui lòng nhập thông tin để thay đổi';
    else if(this.passOld != '' && this.passNew == '') this.mess = 'Đổi mật khẩu thiếu mật khẩu mới';
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
          this.mess = 'Sửa tài khoản thành công';
          this.isSetUser = false;
        }
        else this.mess = 'Sửa tài khoản không thành công vui lòng kiểm tra lại các trường';
      })
    }
    this.isMess = true;
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
  clickCloseMess(){
    this.isMess = false;
    this.mess = '';
  }
  changeMess(){
    return{
      'display':this.isMess ? 'block':'none',
    }
  }
  changeSetUser(){
    return{
      'display':this.isSetUser ? 'block':'none',
    }
  }

  checkUser(){
    if(this.dataAccount != undefined){
      this.isLogin1 = !this.isLogin1;
      this.isLogin2 = !this.isLogin2;
    }
  }
  getBookByBookId(id:number){
    this.productService.getBookByBookId(id).subscribe((res:any)=>{
      this.book = res
    })
  }

  sub(){
    this.count = parseInt(this.inputValue);
    if(this.count > 1){
      this.count -= 1;
      this.inputValue = this.count.toString();
    }
  }

  clickMore(){
    this.isClickMore = !this.isClickMore;
  }
  changeMore(){
    return{
      'display':this.isClickMore ? 'block' : 'none'
    };
  }
  sendInputValue(){
    let data = { message: this.inputValueSearch };
    this.router.navigate(['/moreBook', data]);
  }
  getAllCategory(){
    this.productService.getAllCategory().subscribe((res:any)=>{
      this.datasCategory = res
    })
  }

  sendByCategory(category:Category){
    let data = { message: category.name};
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
    this.valueSave.clear();
    this.dataAccount = undefined;
  }
  send(value:string) {
    let data = { message: value, cardNumber: this.dataAccount?.cardNumber};
    this.router.navigate(['/moreBook', data]);
  }
  clickSaveBuy(book:Book, cost:number){
    if(this.dataAccount != undefined){
      const url1 = 'http://localhost:8080/buy/addNewBuy';
      const data1 = { bookStorageId: book.id, accountId: this.dataAccount?.cardNumber, status: 'Đơn hàng đang được người bán chuẩn bị', cost: cost, quantity:Number(this.inputValue)};
      const url2 = 'http://localhost:8080/notification/addNewNotification';
      const data2 = {mainContentId: 2, accountId: this.dataAccount?.cardNumber};
      let buy:Buy[]=[]
      this.productService.postData(url1, data1).subscribe((res:any) => {
        buy = res;
        if(buy.length != 0) this.mess = 'đã đặt hàng thành công';
        else this.mess = 'đã đặt hàng thất bại';
        this.productService.postData(url2, data2).subscribe();
        this.isClickBuy = !this.isClickBuy;
      });
    }
    else {
      this.clickLogin();
    }
  }
  changeMessBuy(){
    return{
      'display':this.isClickBuy ? 'block':'none',
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
  clickLogin(){
    this.isClickLogin = !this.isClickLogin;
  }
  changeLogin(){
    return{
      'display':this.isClickLogin ? 'block' : 'none'
    };
  }
  clickNewUser(){
    this.isClickNewUser = !this.isClickNewUser;
  }

  changeNewUser(){
    return{
      'display':this.isClickNewUser ? 'block' : 'none'
    }
  }
  clickAddUser(){
    if(this.inputValueName !='' && this.inputValueAddEmail !='' && this.inputValuePhone !='' && this.inputValueAddress !='' && this.inputValueAddPass !=''){
      const url = 'http://localhost:8080/account/addNewAccount';
      const data = { username: this.inputValueName, email: this.inputValueAddEmail, phone: this.inputValuePhone,
        address: this.inputValueAddress, password: this.inputValueAddPass, type: 'user' };
      this.productService.postData(url, data).subscribe();
    }
  }

  clickSaveCart(book:Book){
    if(this.dataAccount != undefined){
      const url = 'http://localhost:8080/cart/addNewCart';
      const data = { bookStorageId: book.id, accountId: this.dataAccount.cardNumber};
      let cart:Cart[]=[]
      this.productService.postData(url, data).subscribe((res:any) => {
        cart = res;
        if(cart.length != 0) this.mess = "Thêm vào giỏ hàng thành công";
        else this.mess = 'Thêm vào giỏ hàng thất bại'
        this.isMess = true;
      });
    }
    else this.clickLogin();
  }
}
