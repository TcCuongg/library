import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Account, Book, Category} from "../app.module";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{
  datasCategory:Category[]=[];
  data: any;
  book:Book | undefined;
  inputValue: string = '';
  inputValueEmail = '';
  inputValuePass = '';
  dataAccount:Account | undefined;

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
    this.checkFontEnd();
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
    }
  }
  getBookByBookId(id:number){
    this.productService.getBookByBookId(id).subscribe((res:any)=>{
      this.book = res
    })
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
    let data = { message: this.inputValue };
    this.router.navigate(['/moreBook', data]);
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
  getAllCategory(){
    this.productService.getAllCategory().subscribe((res:any)=>{
      this.datasCategory = res
    })
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
      }
      else if(this.dataAccount?.type == 'admin' && this.dataAccount.status != 'close'){
        let data = { message: this.dataAccount?.email};
        this.router.navigate(['/manageUser', data]);
      }
    }
  }
  send(value:string) {
    if(this.dataAccount != undefined){
      let data = { message: value, cardNumber: this.dataAccount.cardNumber};
      this.router.navigate(['/moreBook', data]);
    }
    else this.clickLogin();
  }
  clickSaveCart(book:Book){
    if(this.dataAccount != undefined){
      const url = 'http://localhost:8080/cart/addNewCart';
      const data = { bookStorageId: book.id, accountId: this.dataAccount.cardNumber};
      this.productService.postData(url, data).subscribe();
    }
    else this.clickLogin();
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
