import {Component, Inject, LOCALE_ID, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {Account, Book} from "../app.module";
import {ActivatedRoute, Router} from "@angular/router";
import {isTemplateDiagnostic} from "@angular/compiler-cli/src/ngtsc/typecheck/diagnostics";

@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.css']
})
export class ManageBookComponent implements OnInit {
  datasBook:Book[]=[];
  datasCheckBook:Book[]=[];
  book:Book | undefined;
  dataBookManage:Book[]=[];
  dataCategory:string[]=[];
  dataAuthor:string[]=[];
  dataStatus:string[]=[];

  Min = '';
  Max = '';
  categoryName:string | null = null;
  authorName:string | null = null;
  bookStatus:string | null = null;
  bookCost:string | null = null;

  data:any;

  count = 0;
  inputValue: string = '';
  inputTitle = '';
  inputCategory = '';
  inputAuthor = '';
  inputContent = '';
  inputCost = '';
  inputSale = '';
  inputStatus = '';

  valueSave:any;


  admin:Account | undefined;

  logOut = false;
  isCheck=false;
  isClickCategoryMore = false;
  isClickAuthorMore = false;
  isClickCostMore = false;
  isClickStatusMore = false;

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              @Inject(LOCALE_ID) private locale: string) {}
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.data = params;
    });
    this.getAll(this.count);
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
    this.getAllCategoryName();
    this.getAllAuthorName();
    this.getAllStatus();
  }

  selectCategory(categoryName:string){
    this.categoryName = categoryName;
  }
  selectAuthor(authorName:string){
    this.authorName = authorName;
  }
  selectBookStatus(bookStatus:string){
    this.bookStatus = bookStatus;
  }

  clickCategoryMore(){
    this.isClickCategoryMore = !this.isClickCategoryMore;
    this.isClickStatusMore = false;
    this.isClickCostMore = false;
    this.isClickAuthorMore = false;
  }
  changeCategoryMore(){
    return{
      'display':this.isClickCategoryMore ? 'block':'none'
    }
  }
  clickAuthorMore(){
    this.isClickAuthorMore = !this.isClickAuthorMore;
    this.isClickStatusMore = false;
    this.isClickCostMore = false;
    this.isClickCategoryMore = false;
  }
  changeAuthorMore(){
    return{
      'display':this.isClickAuthorMore ? 'block':'none'
    }
  }
  clickCostMore(){
    this.isClickCostMore = !this.isClickCostMore;
    this.isClickStatusMore = false;
    this.isClickAuthorMore = false;
    this.isClickCategoryMore = false;
  }
  changeCostMore(){
    return{
      'display':this.isClickCostMore ? 'block':'none'
    }
  }
  clickStatusMore(){
    this.isClickStatusMore = !this.isClickStatusMore;
    this.isClickCostMore = false;
    this.isClickAuthorMore = false;
    this.isClickCategoryMore = false;
  }
  changeStatusMore(){
    return{
      'display':this.isClickStatusMore ? 'block':'none'
    }
  }


  getAllCategoryName(){
    this.productService.getAllCategoryName().subscribe((res:any) => {
      this.dataCategory = res
    })
  }

  getAllAuthorName(){
    this.productService.getAllAuthorName().subscribe((res:any) => {
      this.dataAuthor = res
    })
  }

  getAllStatus(){
    this.productService.getAllStatus().subscribe((res:any) => {
      this.dataStatus = res
    })
  }

  changeColorTextStorage(){
    if(this.data.messageFromManageStyle != undefined){
      return{
        'color' : 'white',
      }
    }
    else return {
      'color' : 'red',
    }
  }

  changeColorTextStyle(){
    if(this.data.messageFromManageStyle != undefined){
      return{
        'color' : 'red',
      }
    }
    else return {
      'color' : 'white',
    }
  }
  changeBook(){
    const url = 'http://localhost:8080/book/updateBook/0/100';
    const body = {bookId: this.book?.bookId, authorId: this.book?.authorId,
      title: this.inputTitle, cost: Number(this.inputCost), status: this.inputStatus,
      category: this.inputCategory, author: this.inputAuthor, sale: Number(this.inputSale), content: this.inputContent}
    this.productService.postData(url, body).subscribe((res:any)=>{
      this.dataBookManage = res
    });
    if(this.dataBookManage.length == 0){
      this.isCheck = true;
    }
    else this.getAll(this.count)
  }

  clickBook(book:Book){
    this.book = book;
    this.inputTitle = this.book.title ?? '';
    this.inputCategory = this.book.category ?? '';
    this.inputAuthor = this.book.author ?? '';
    this.inputContent = this.book.content ?? '';
    this.inputCost = (this.book.cost ?? 0).toString();
    this.inputSale = (this.book.sale ?? 0).toString();
    this.inputStatus = this.book.status ?? '';
  }

  getAll(n:number){
    if(this.data.messageFromManageStyle != undefined){
      this.productService.getBookManageByRequest(this.data.messageFromManageStyle, this.count, 11).subscribe((res:any)=>{
        this.datasBook = res
      })
    }
    else {
      this.productService.getAllBookManage(n, 11).subscribe((res:any)=>{
        this.datasBook = res
      })
    }
  }
  clickSearch(){
    this.count = 0;
    this.productService.getBookManageByRequest(this.inputValue, this.count, 11).subscribe((res:any)=>{
      this.datasBook = res
    })
  }
  clickMore(){
    if (this.datasBook.length==9){
      this.count = this.count + 1;
      this.datasCheckBook = this.datasBook;
      this.getAll(this.count);
      if(this.datasBook == undefined){
        this.datasBook = this.datasCheckBook;
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      this.getAll(this.count);
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
  clickManageBook(){
    this.count = 0;
    this.data.messageFromManageStyle = undefined;
    this.getAll(this.count);
  }

  isSetting:any[]=[{status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}]
  changeText(index: number) {
    this.isSetting[index].status = !this.isSetting[index].status;
  }

  isNewBook = true;
  clickNewBook(){
    this.isNewBook = !this.isNewBook;
  }

  changNewBook(){
    return{
      'display':this.isNewBook ? 'none':'block',
    }
  }

  changWeb(){
    return{
      'opacity':this.isNewBook ? '1':'0.2',
    }
  }
}
