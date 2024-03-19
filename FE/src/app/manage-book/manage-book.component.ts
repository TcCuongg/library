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
  isCheck = false;

  Min = '';
  Max = '';
  categoryName = 'Tất cả thể loại';
  authorName = 'Tất cả tác giả';
  bookStatus = 'Tất cả trạng thái';
  bookCost:string | null = null;

  data:any;

  count = 0;
  inputValue: string = '';
  inputTitle: string[] = new Array(11).fill("");
  inputCategory: string[] = new Array(11).fill("");
  inputAuthor: string[] = new Array(11).fill("");
  inputContent: string[] = new Array(11).fill("");
  inputCost: string[] = new Array(11).fill("");
  inputSale: string[] = new Array(11).fill("");
  inputStatus: string[] = new Array(11).fill("");

  valueSave:any;


  admin:Account | undefined;

  logOut = false;
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
    this.clickCategoryMore();
  }
  selectAuthor(authorName:string){
    this.authorName = authorName;
    this.clickAuthorMore();
  }
  selectBookStatus(bookStatus:string){
    this.bookStatus = bookStatus;
    this.clickStatusMore();
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
  changeBook(i:number){
    const url = 'http://localhost:8080/book/updateBook/'+this.count+'/11';
    const body = {bookId: this.book?.bookId, authorId: this.book?.authorId,
      title: this.inputTitle[i], cost: Number(this.inputCost[i]), status: this.inputStatus[i],
      category: this.inputCategory[i], author: this.inputAuthor[i], sale: Number(this.inputSale[i]), content: this.inputContent[i]}
    this.productService.postData(url, body).subscribe((res:any)=>{
      this.datasBook = res
    });
  }

  clickBook(book:Book, i:number){
    this.book = book;
    this.inputTitle[i] = this.book.title ?? '';
    this.inputCategory[i] = this.book.category ?? '';
    this.inputAuthor[i] = this.book.author ?? '';
    this.inputContent[i] = this.book.content ?? '';
    this.inputCost[i] = (this.book.cost ?? 0).toString();
    this.inputSale[i] = (this.book.sale ?? 0).toString();
    this.inputStatus[i] = this.book.status ?? '';
  }

  clickBack(i:number){
    this.inputTitle[i] = '';
    this.inputCategory[i] = '';
    this.inputAuthor[i] = '';
    this.inputContent[i] = '';
    this.inputCost[i] = '';
    this.inputSale[i] = '';
    this.inputStatus[i] = '';
  }
  clickSearch(){
    this.count = 0;
    this.productService.getBookManageByRequest(this.inputValue, this.count, 11).subscribe((res:any)=>{
      this.datasBook = res
    })
  }

  getAll(n:number){
    if(this.isCheck == false){
      if(this.data.messageFromManageStyle != undefined){
        this.productService.getBookManageByRequest(this.data.messageFromManageStyle, n, 11).subscribe((res:any)=>{
          this.datasBook = res
        })
      }
      else {
        this.productService.getAllBookManage(n, 11).subscribe((res:any)=>{
          this.datasBook = res
        })
      }
    }
    else {
      let category = '';
      let author = '';
      let status = '';
      const url = 'http://localhost:8080/book/getSelectBook/' + n + '/11';
      if(this.categoryName != 'Tất cả thể loại') category = this.categoryName;
      if(this.authorName != 'Tất cả tác giả') author = this.authorName;
      if(this.bookStatus != 'Tất cả trạng thái') status = this.bookStatus;
      const body = {category: category, author: author, costStart: this.Min, costEnd: this.Max, status: status}
      this.productService.postData(url, body).subscribe((res:any) => {
        this.datasBook = res
      })
    }
  }
  clickMore(){
    if (this.datasBook.length==11){
      if(this.isCheck == false){
        if(this.data.messageFromManageStyle != undefined){
          this.productService.getBookManageByRequest(this.data.messageFromManageStyle, this.count + 1, 11).subscribe((res:any)=>{
            this.datasCheckBook = res
          })
        }
        else {
          this.productService.getAllBookManage(this.count + 1, 11).subscribe((res:any)=>{
            this.datasCheckBook = res
          })
        }
      }
      else {
        let category = '';
        let author = '';
        let status = '';
        const url = 'http://localhost:8080/book/getSelectBook/' + this.count + 1 + '/11';
        if(this.categoryName != 'Tất cả thể loại') category = this.categoryName;
        if(this.authorName != 'Tất cả tác giả') author = this.authorName;
        if(this.bookStatus != 'Tất cả trạng thái') status = this.bookStatus;
        const body = {category: category, author: author, costStart: this.Min, costEnd: this.Max, status: status}
        this.productService.postData(url, body).subscribe((res:any) => {
          this.datasCheckBook = res
        })
      }
      if(this.datasCheckBook.length != 0){
        this.count = this.count + 1;
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
    this.productService.getAllBookManage(this.count, 11).subscribe((res:any)=>{
      this.datasBook = res
    })
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

  title = '';
  category = '';
  author = '';
  content = '';
  cost = '';
  sale = '';
  status = '';

  saveNewBook(){
    const url = 'http://localhost:8080/book/addNewBook'
    const body = {bookId: 0, authorId: 0, title: this.title, cost: this.cost, content: this.content, status: this.status, sale: this.sale, category: this.category, author: this.author}
    this.productService.postData(url, body).subscribe();
    this.backSave();
  }

  backSave(){
    this.title = '';
    this.category = '';
    this.author = '';
    this.content = '';
    this.cost = '';
    this.sale = '';
    this.status = '';
  }

  changWeb(){
    return{
      'opacity':this.isNewBook ? '1':'0.2',
    }
  }
  selectBook(){
    this.isCheck = true;
    this.count = 0;
    this.isClickStatusMore = false;
    this.isClickCostMore = false;
    this.isClickAuthorMore = false;
    this.isClickCategoryMore = false;
    let category = '';
    let author = '';
    let status = '';
    const url = 'http://localhost:8080/book/getSelectBook/' + this.count + '/11';
    if(this.categoryName != 'Tất cả thể loại') category = this.categoryName;
    if(this.authorName != 'Tất cả tác giả') author = this.authorName;
    if(this.bookStatus != 'Tất cả trạng thái') status = this.bookStatus;
    const body = {category: category, author: author, costStart: this.Min, costEnd: this.Max, status: status}
    this.productService.postData(url, body).subscribe((res:any) => {
      this.datasBook = res
    })
  }
}
