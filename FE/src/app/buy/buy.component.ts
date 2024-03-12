import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Account, Book, Category} from "../app.module";
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
  dataAccount:Account | undefined;

  valueSave:any;

  isClickMore = false;
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
    const url1 = 'http://localhost:8080/buy/addNewBuy';
    const data1 = { bookStorageId: book.id, accountId: this.dataAccount?.cardNumber, status: 'Thanh toán khi nhận hàng', cost: cost};
    this.productService.postData(url1, data1).subscribe();
    const url2 = 'http://localhost:8080/notification/addNewNotification';
    const data2 = {mainContentId: 2, accountId: this.dataAccount?.cardNumber};
    this.productService.postData(url2, data2).subscribe();
  }
}
