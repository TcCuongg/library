import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PlatformLocation} from "@angular/common";
import {ProductService} from "../product.service";
import {Account, Book, Category} from "../app.module";

@Component({
  selector: 'app-more',
  templateUrl: './more.component.html',
  styleUrls: ['./more.component.css']
})
export class MoreComponent implements OnInit{
  data: any;
  count= 0;
  datasBook:Book[]=[];
  dataAccount:Account | undefined;
  inputValue: string = '';
  inputValueEmail = '';
  inputValuePass = '';
  datasCategory:Category[]=[];
  mess = '';

  inputValueName = '';
  inputValueAddEmail = '';
  inputValuePhone = '';
  inputValueAddress = '';
  inputValueAddPass = '';

  isSetUser = false;
  isMess = false;
  mess1 = '';
  userName = '';
  userEmail = '';
  userPhone = '';
  userAddress = '';
  passOld = '';
  passNew = '';

  valueSave:any;

  isClickMore = false;
  isClickLogin = false;
  isClickNewUser = false;
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
    this.mess = this.data.message ?? '';
    this.getAllCategory();
    this.valueSave = window.localStorage;
    this.dataAccount = JSON.parse(this.valueSave.getItem('value'));
    this.checkUser();
    this.checkFontEnd();
    this.getBooks(this.count);
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
    this.mess1 = '';
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
    if(Number(this.data.storage) > 0){
      if(this.data.message == undefined && this.mess == ''){
        this.productService.getBookByStorage(Number(this.data.storage), count, 8).subscribe((res:any) => {
          this.datasBook = res;
        })
        this.productService.getCountBookByStorage(Number(this.data.storage)).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/8);
        })
      }
      if(this.data.message == undefined && this.mess != "Truyện đề cử" && this.mess != "Truyện mới" && this.mess != "Giỏ hàng"){
        this.productService.getBookByStorageIdAndCategory(this.data.storage, this.mess, count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
        this.productService.getCountBookByStorageIdAndCategory(Number(this.data.storage), this.mess).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/8);
        })
      }
    }
    else {
      if(this.mess == "Truyện đề cử"){
        this.productService.getBookFollowDesc(count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
        this.productService.getCountBookFollowDesc().subscribe((res:any) => {
          this.totalPages = Math.ceil(res/8);
        })
      }
      else if(this.mess == "Truyện mới"){
        this.productService.getAllBook(count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
        this.productService.getCountAllBook().subscribe((res:any) => {
          this.totalPages = Math.ceil(res/8);
        })
      }
      else if(this.mess == "Giỏ hàng"){
        this.productService.getAllBookByAccountCart((this.dataAccount?.cardNumber ?? 0), count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
        this.productService.getCountAllBookByAccountCart((this.dataAccount?.cardNumber ?? 0)).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/8);
        })
      }
      else if(this.mess == "Truyện mới đặt"){
        this.productService.getAllBookByAccountBuyNew(this.dataAccount?.cardNumber ?? 0, count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        });
        this.productService.getCountAllBookByAccountBuyNew((this.dataAccount?.cardNumber ?? 0)).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/8);
        })
      }
      else if(this.mess == "Truyện đang giao"){
        this.productService.getAllBookByAccountBuyDelivering(this.dataAccount?.cardNumber ?? 0, count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        });
        this.productService.getCountAllBookByAccountBuyDelivering((this.dataAccount?.cardNumber ?? 0)).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/8);
        })
      }
      else if(this.mess == "Truyện đã giao"){
        this.productService.getAllBookByAccountBuyDelivered(this.dataAccount?.cardNumber ?? 0, count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        });
        this.productService.getCountAllBookByAccountBuyDelivered((this.dataAccount?.cardNumber ?? 0)).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/8);
        })
      }
      else if(this.mess != "Truyện đề cử" && this.mess != "Truyện mới" && this.mess != "Giỏ hàng" && this.mess != "Truyện mới đặt" && this.mess != "Truyện đang giao" && this.mess != "Truyện đã giao"){
        this.productService.getBookByCategory(this.mess, count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        });
        this.productService.getCountBookByCategory(this.mess).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/8);
        })
      }
    }
  }
  clickMore(){
    if (this.count < this.totalPages - 1){
      this.count += 1;
      if(Number(this.data.storage) > 0){
        if(this.data.message == undefined && this.mess == ''){
          this.productService.getBookByStorage(Number(this.data.storage), this.count, 8).subscribe((res:any) => {
            this.datasBook = res;
          })
        }
        if(this.data.message == undefined && this.mess != "Truyện đề cử" && this.mess != "Truyện mới" && this.mess != "Giỏ hàng"){
          this.productService.getBookByStorageIdAndCategory(this.data.storage, this.mess, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
      }
      else {
        if(this.mess == "Truyện đề cử"){
          this.productService.getBookFollowDesc(this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Truyện mới"){
          this.productService.getAllBook(this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Giỏ hàng"){
          this.productService.getAllBookByAccountCart((this.dataAccount?.cardNumber ?? 0), this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Truyện mới đặt"){
          this.productService.getAllBookByAccountBuyNew(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Truyện đang giao"){
          this.productService.getAllBookByAccountBuyDelivering(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Truyện đã giao"){
          this.productService.getAllBookByAccountBuyDelivered(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess != "Truyện đề cử" && this.mess != "Truyện mới" && this.mess != "Giỏ hàng" && this.mess != "Truyện mới đặt" && this.mess != "Truyện đang giao" && this.mess != "Truyện đã giao"){
          this.productService.getBookByCategory(this.mess, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      if(Number(this.data.storage) > 0){
        if(this.data.message == undefined && this.mess == ''){
          this.productService.getBookByStorage(Number(this.data.storage), this.count, 8).subscribe((res:any) => {
            this.datasBook = res;
          })
        }
        if(this.data.message == undefined && this.mess != "Truyện đề cử" && this.mess != "Truyện mới" && this.mess != "Giỏ hàng"){
          this.productService.getBookByStorageIdAndCategory(this.data.storage, this.mess, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
      }
      else {
        if(this.mess == "Truyện đề cử"){
          this.productService.getBookFollowDesc(this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Truyện mới"){
          this.productService.getAllBook(this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Giỏ hàng"){
          this.productService.getAllBookByAccountCart((this.dataAccount?.cardNumber ?? 0), this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Truyện mới đặt"){
          this.productService.getAllBookByAccountBuyNew(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Truyện đang giao"){
          this.productService.getAllBookByAccountBuyDelivering(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess == "Truyện đã giao"){
          this.productService.getAllBookByAccountBuyDelivered(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
        else if(this.mess != "Truyện đề cử" && this.mess != "Truyện mới" && this.mess != "Giỏ hàng" && this.mess != "Truyện mới đặt" && this.mess != "Truyện đang giao" && this.mess != "Truyện đã giao"){
          this.productService.getBookByCategory(this.mess, this.count, 8).subscribe((res:any)=>{
            this.datasBook = res;
          })
        }
      }
    }
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
    this.productService.getCountBookByTitle(this.inputValue).subscribe((res:any) => {
      this.totalPages = Math.ceil(res/8);
    })
  }

  sendBookContent(name:Book){
    let data = { message: name.id};
    this.router.navigate(['/Buy', data]);
  }

  getAllCategory(){
    this.productService.getAllCategory().subscribe((res:any)=>{
      this.datasCategory = res
    })
  }
  value='';
  clickCategory(category:string){
    this.mess = category;
    this.count = 0;
    this.getBooks(this.count);
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


  buy(){
    return{
      'display': this.isBuy ? 'block' : 'none'
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
  clickCart(){
    this.count = 0;
    this.mess = 'Giỏ hàng';
    if(this.dataAccount?.cardNumber != undefined){
      this.productService.getAllBookByAccountCart(this.dataAccount.cardNumber, this.count, 8).subscribe((res:any)=>{
        this.datasBook = res;
      });
      this.productService.getCountAllBookByAccountCart((this.dataAccount?.cardNumber ?? 0)).subscribe((res:any) => {
        this.totalPages = Math.ceil(res/8);
      })
    }
    else {
      this.clickLogin();
    }
  }

  clickAddUser(){
    if(this.inputValueName !='' && this.inputValueAddEmail !='' && this.inputValuePhone !='' && this.inputValueAddress !='' && this.inputValueAddPass !=''){
      const url = 'http://localhost:8080/account/addNewAccount';
      const data = { username: this.inputValueName, email: this.inputValueAddEmail, phone: this.inputValuePhone,
        address: this.inputValueAddress, password: this.inputValueAddPass , type:'user'};
      this.productService.postData(url, data).subscribe();
    }
  }


  totalPages = 1;
  currentPage: number = 1;
  visiblePages: number = 3;

  getVisiblePages(): number[] {
    let pages: number[] = [];
    let startPage: number = Math.max(this.count, 1);
    let endPage: number = Math.min(startPage + this.visiblePages - 1, this.totalPages - 1);
    if(endPage)
      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }
    return pages;
  }

  selectPage(page: number) {
    this.count = page - 1;
    if(Number(this.data.storage) > 0){
      if(this.data.message == undefined && this.mess == ''){
        this.productService.getBookByStorage(Number(this.data.storage), this.count, 8).subscribe((res:any) => {
          this.datasBook = res;
        })
      }
      if(this.data.message == undefined && this.mess != "Truyện đề cử" && this.mess != "Truyện mới" && this.mess != "Giỏ hàng"){
        this.productService.getBookByStorageIdAndCategory(this.data.storage, this.mess, this.count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
      }
    }
    else {
      if(this.mess == "Truyện đề cử"){
        this.productService.getBookFollowDesc(this.count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
      }
      else if(this.mess == "Truyện mới"){
        this.productService.getAllBook(this.count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
      }
      else if(this.mess == "Giỏ hàng"){
        this.productService.getAllBookByAccountCart((this.dataAccount?.cardNumber ?? 0), this.count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
      }
      else if(this.mess == "Truyện mới đặt"){
        this.productService.getAllBookByAccountBuyNew(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
      }
      else if(this.mess == "Truyện đang giao"){
        this.productService.getAllBookByAccountBuyDelivering(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
      }
      else if(this.mess == "Truyện đã giao"){
        this.productService.getAllBookByAccountBuyDelivered(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
      }
      else if(this.mess != "Truyện đề cử" && this.mess != "Truyện mới" && this.mess != "Giỏ hàng" && this.mess != "Truyện mới đặt" && this.mess != "Truyện đang giao" && this.mess != "Truyện đã giao"){
        this.productService.getBookByCategory(this.mess, this.count, 8).subscribe((res:any)=>{
          this.datasBook = res;
        })
      }
    }
  }
}
