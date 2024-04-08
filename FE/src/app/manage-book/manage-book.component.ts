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

  isSetUser = false;
  userName = '';
  userEmail = '';
  userPhone = '';
  userAddress = '';
  passOld = '';
  passNew = '';

  data:any;

  count = 0;
  inputValue: string = '';


  title:string = '';
  category:string = '';
  author:string = '';
  content:string = '';
  cost:string = '';
  sale:string = '';
  status:string = '';

  valueSave:any;


  admin:Account | undefined;

  logOut = false;
  isClickCategoryMore = false;
  isClickAuthorMore = false;
  isClickCostMore = false;
  isClickStatusMore = false;
  isClickSetBook = false;
  isClickAddBook = false;
  isMoreSelectCategory = false;
  isMoreSelectAuthor = false;
  isBoxInput = false;

  constructor(private productService: ProductService,
              private route: ActivatedRoute) {}
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

  setUser(){
    let userName = this.admin?.name;
    let userEmail = this.admin?.email;
    let userPhone = this.admin?.phone?.toString();
    let userAddress = this.admin?.address;
    let account:Account;
    if(this.userName != '') userName = this.userName;
    if(this.userEmail != '') userEmail = this.userEmail;
    if(this.userPhone != '') userPhone = this.userPhone;
    if(this.userAddress != '') userAddress = this.userAddress;

    if(this.userName + this.userEmail + this.userPhone + this.userAddress + this.passOld + this.passNew == '') this.mess = 'Vui lòng nhập thông tin để thay đổi';
    else if(this.passOld != '' && this.passNew == '') this.mess = 'Đổi mật khẩu thiếu mật khẩu mới';
    else if(this.passOld == '' && this.passNew != '') this.mess = 'Đổi mật khẩu phải nhập mật khẩu cũ';
    else{
      const body = {userId: this.admin?.cardNumber, userName:userName, userEmail:userEmail, userPhone:userPhone
        , userAddress:userAddress, passOld:this.passOld, passNew:this.passNew};
      this.productService.postData('http://localhost:8080/account/setUser', body).subscribe((res:any) => {
        account = res;
        if(account.address != null){
          this.valueSave.clear();
          this.valueSave.setItem('value', JSON.stringify(account));
          this.admin = JSON.parse(this.valueSave.getItem('value'));
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
  changeSetUser(){
    return{
      'display':this.isSetUser ? 'block':'none',
    }
  }

  changeSetBook(){
    return{
      'display':this.isBoxInput ? 'block':'none',
    }
  }

  clickMoreSelectCategory(){
    this.isMoreSelectCategory = !this.isMoreSelectCategory;
  }

  changeMoreSelectCategory(){
    return{
      'display': this.isMoreSelectCategory ? 'block':'none',
    }
  }

  clickSelectCategory(string:string){
    this.category = string;
  }

  changeMoreSelectAuthor(){
    return{
      'display': this.isMoreSelectAuthor ? 'block':'none',
    }
  }

  clickMoreSelectAuthor(){
    this.isMoreSelectAuthor = !this.isMoreSelectAuthor;
  }

  clickSelectAuthor(string:string){
    this.author = string;
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
  dataBookChange:Book[] = [];
  changeBook(){
    const url = 'http://localhost:8080/book/updateBook/'+this.count+'/11';
    const body = {bookId: this.book?.bookId, authorId: this.book?.authorId, title: this.title, cost: this.cost.valueOf(), status: this.status,
      category: this.category, author: this.author, content: this.content, sale: this.sale.valueOf()}
    this.productService.postData(url, body).subscribe((res:any)=>{
      this.dataBookChange = res;
      if(this.dataBookChange.length > 0){
        this.mess = 'Sửa thông tin sách thành công';
      }else this.mess = 'Sửa thông tin không thành công vui lòng nhập đủ các trường';
      this.isMess = true;
      this.getAll(this.count);
    });
  }

  clickBook(book:Book){
    this.book = book;
    this.category = this.book.category ?? '';
    this.author = this.book.author ?? '';
    this.status = this.book.status ?? '';
    this.title = this.book.title ?? '';
    this.content = this.book.content ?? '';
    this.cost = this.book.cost?.toString() ?? '';
    this.sale = this.book.sale?.toString() ?? '';
  }

  clickBack(){
    this.isBoxInput = false;
    this.isClickSetBook = false;
    this.isClickAddBook = false;
    this.backSave();
  }
  clickSearch(){
    this.count = 0;
    this.productService.getBookManageByRequest(this.inputValue, this.count, 11).subscribe((res:any)=>{
      this.datasBook = res
    })
    this.productService.getCountAllBookPageByRequest(this.inputValue).subscribe((res:any) => {
      this.totalPages = Math.ceil(res/11);
    })
  }

  getAll(n:number){
    if(this.isCheck == false){
      if(this.data.messageFromManageStyle != undefined){
        this.productService.getBookManageByRequest(this.data.messageFromManageStyle, n, 11).subscribe((res:any)=>{
          this.datasBook = res;
        })
        this.productService.getCountAllBookPageByRequest(this.data.messageFromManageStyle).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/11);
        })
      }
      else {
        this.productService.getAllBookManage(n, 11).subscribe((res:any)=>{
          this.datasBook = res
        })
        this.productService.getCountAllBookManage().subscribe((res:any) => {
          this.totalPages = Math.ceil(res/11);
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
    if (this.count < this.totalPages - 1){
      this.count += 1;
      if(this.isCheck == false){
        if(this.data.messageFromManageStyle != undefined){
          this.productService.getBookManageByRequest(this.data.messageFromManageStyle, this.count, 11).subscribe((res:any)=>{
            this.datasBook = res
          })
        }
        else {
          this.productService.getAllBookManage(this.count, 11).subscribe((res:any)=>{
            this.datasBook = res
          })
        }
      }
      else {
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
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      if(this.isCheck == false){
        if(this.data.messageFromManageStyle != undefined){
          this.productService.getBookManageByRequest(this.data.messageFromManageStyle, this.count, 11).subscribe((res:any)=>{
            this.datasBook = res
          })
        }
        else {
          this.productService.getAllBookManage(this.count, 11).subscribe((res:any)=>{
            this.datasBook = res
          })
        }
      }
      else {
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

  dataBookNew: Book | undefined;
  isCheckNew = false;
  saveNewBook(){
    this.mess = 'Thêm sách thất bại vui lòng kiểm tra lại các trường';
    const url = 'http://localhost:8080/book/addNewBook';
    const body = {bookId: 0, authorId: 0, title: this.title, cost: this.cost, content: this.content, status: this.status, sale: this.sale, category: this.category, author: this.author}
    this.productService.postData(url, body).subscribe((res:any)=>{
      this.dataBookNew = res;
      this.isCheckNew = true;
      if(this.isCheckNew == true){
        this.mess = 'Thêm sách thành công';
      }
    });
    this.isMess = true;
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
      'opacity':this.isBoxInput || this.isMess ? '0.2':'1',
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
    this.productService.postData('http://localhost:8080/book/getCountSelectBookManage', body).subscribe((res:any) => {
      this.totalPages = Math.ceil(res/11);
    })
  }

  changeHeight(){
    return{
      'height': this.isMoreSelectCategory ? '28%':'10%',
    }
  }

  optionStatus(event: any) {
    this.status = event.target.value;
  }
  mess = '';
  isMess = false;
  changeMess(){
    return{
      'display':this.isMess ? 'block':'none',
    }
  }
  clickCloseMess(){
    this.isMess = !this.isMess;
    this.mess = '';
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
    if(this.isCheck == false){
      if(this.data.messageFromManageStyle != undefined){
        this.productService.getBookManageByRequest(this.data.messageFromManageStyle, this.count, 11).subscribe((res:any)=>{
          this.datasBook = res
        })
      }
      else {
        this.productService.getAllBookManage(this.count, 11).subscribe((res:any)=>{
          this.datasBook = res
        })
      }
    }
    else {
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
}
