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
  datasCheckBook:Book[]=[];
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

  valueSave:any;

  isClickMore = false;
  isClickLogin = false;
  isClickNewUser = false;
  isLogin1 = true;
  isLogin2 = false;
  logOut = false;
  isBuy = false;
  isCheck = false;
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
    if(this.mess == "Truyện đề cử"){
      this.productService.getBookFollowDesc(count, 8).subscribe((res:any)=>{
        this.datasBook = res
      })
    }
    else if(this.mess == "Truyện mới"){
      this.productService.getAllBook(count, 8).subscribe((res:any)=>{
        this.datasBook = res
      })
    }
    else if(this.mess == "Giỏ hàng"){
      this.productService.getAllBookByAccountCart((this.dataAccount?.cardNumber ?? 0), count, 8).subscribe((res:any)=>{
        this.datasBook = res
      });
    }
    else if(this.mess != "Truyện đề cử" && this.mess != "Truyện mới" && this.mess != "Giỏ hàng"){
      this.productService.getBookByCategory(this.mess, count, 8).subscribe((res:any)=>{
        this.datasBook = res
      })
    }
    if(this.datasBook.length == 0){
      this.productService.getBookByRequest(this.mess, count, 8).subscribe((res:any)=>{
        this.datasBook = res
      })
    }
  }

  clickBuy(){
    this.count = 0;
    this.isCheck = true;
    this.productService.getAllBookByAccountBuy(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
      this.datasBook = res
    });
  }

  clickMore(){
    if (this.datasBook.length==8){
      this.count = this.count + 1;
      this.datasCheckBook = this.datasBook;
      if(!this.isCheck){
        this.getBooks(this.count);
        if(this.datasBook == undefined){
          this.datasBook = this.datasCheckBook;
        }
      }else {
        this.productService.getAllBookByAccountBuy(this.dataAccount?.cardNumber ?? 0, this.count, 8).subscribe((res:any)=>{
          this.datasBook = res
        });
        if(this.datasBook == undefined){
          this.datasBook = this.datasCheckBook;
        }
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      this.getBooks(this.count);
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
  }

  sendBookContent(name:Book){
    let data = { message: name.id};
    this.router.navigate(['/Search', data]);
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
    this.valueSave.clear();
    this.dataAccount = undefined;
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
        this.valueSave.setItem('value', JSON.stringify(this.dataAccount));
        this.dataAccount = JSON.parse(this.valueSave.getItem('value'));
        this.getBooks(this.count);
      }
      else if(this.dataAccount?.type == 'admin' && this.dataAccount.status != 'close'){
        let data = { message: this.dataAccount?.email};
        this.router.navigate(['/manageUser', data]);
      }
    }
  }
  clickCart(){
    if(this.dataAccount?.cardNumber != undefined){
      this.count = 0;
      this.productService.getAllBookByAccountCart(this.dataAccount.cardNumber, this.count, 8).subscribe((res:any)=>{
        this.datasBook = res
      });
    }
    else {
      this.count = 0;
      this.mess = 'Giỏ hàng';
      this.clickLogin();
    }
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
