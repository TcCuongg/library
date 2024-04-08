import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Account, Book} from "../app.module";
import {DatePipe} from "@angular/common";
import { DateTime } from "luxon";

@Component({
  selector: 'app-detail-storage',
  templateUrl: './detail-storage.component.html',
  styleUrls: ['./detail-storage.component.css']
})
export class DetailStorageComponent implements OnInit{
  data: any;
  count = 0;
  datasBook:Book[]=[];
  datasCheckBook:Book[]=[];
  dataCategory:string[]=[];
  dataAuthor:string[]=[];
  inputValue='';
  book:Book | undefined;
  categoryName = 'Tất cả thể loại';
  authorName = 'Tất cả tác giả';

  valueSave:any;

  timeStart = '';
  timeEnd = '';
  min = '';
  max = '';
  changeQuantity = '';

  isSetUser = false;
  userName = '';
  userEmail = '';
  userPhone = '';
  userAddress = '';
  passOld = '';
  passNew = '';


  admin:Account | undefined;

  logOut = false;
  isClickCategoryMore = false;
  isClickAuthorMore = false;
  isClickImportTimeMore = false;
  isClickQuantityMore = false;
  changeBook = false;
  isMess = false;


  inputImage:any;
  inputImportTime='';
  inputQuantity='';

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) { }
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.data = params;
    });
    this.display();
    this.getAllCategoryName();
    this.getAllCategoryName();
    this.getAllStatus();
    this.getAllAuthorName();
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
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

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = (e) => {
      this.inputImage = e.target?.result;
    };
    reader.readAsDataURL(file);
  }

  onFileAdd(event: any): void {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = (e) => {
      this.inputImage = e.target?.result;
    };
    reader.readAsDataURL(file);
  }

  clickImportTimeMore(){
    this.isClickImportTimeMore = !this.isClickImportTimeMore;
    this.isClickCategoryMore = false;
    this.isClickQuantityMore = false;
    this.isClickAuthorMore = false;
  }

  changeImportTimeMore(){
    return{
      'display':this.isClickImportTimeMore ? 'block':'none'
    }
  }

  changeQuantityMore(){
    return{
      'display':this.isClickQuantityMore ? 'block':'none'
    }
  }

  clickQuantityMore(){
    this.isClickQuantityMore = !this.isClickQuantityMore;
    this.isClickCategoryMore = false;
    this.isClickImportTimeMore = false;
    this.isClickAuthorMore = false;
  }

  selectCategory(categoryName:string){
    this.categoryName = categoryName;
    this.clickCategoryMore();
  }

  clickCategoryMore(){
    this.isClickCategoryMore = !this.isClickCategoryMore;
    this.isClickImportTimeMore = false;
    this.isClickQuantityMore = false;
    this.isClickAuthorMore = false;
  }

  selectAuthor(authorName:string){
    this.authorName = authorName;
    this.clickAuthorMore();
  }

  clickAuthorMore(){
    this.isClickAuthorMore = !this.isClickAuthorMore;
    this.isClickImportTimeMore = false;
    this.isClickQuantityMore = false;
    this.isClickCategoryMore = false;
  }

  changeCategoryMore(){
    return{
      'display':this.isClickCategoryMore ? 'block':'none'
    }
  }

  changeAuthorMore(){
    return{
      'display':this.isClickAuthorMore ? 'block':'none'
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

  clickBook(book:Book){
    this.book = book;
    let datePipe = new DatePipe('en-US');
    this.inputImage=this.book.image;
    this.inputImportTime = (datePipe.transform(this.book.importTime, 'yyyy-MM-dd HH:mm:ss') ?? '').toString();
    this.inputQuantity=(this.book.quantity ?? '').toString();
    this.getTimeNow();
  }

  display(){
    if(this.data.message1 != undefined){
      this.productService.getBookByStorageAndRequest(this.data.message, this.data.message1, this.count, 7).subscribe((res:any)=>{
        this.datasBook = res;
      });
      this.productService.getCountAllBookByStorageAndRequest(this.data.message, this.data.message1).subscribe((res:any) => {
        this.totalPages = Math.ceil(res/7);
      })
    }
    else {
      this.getBookByStorage(this.data.message, this.count, 7)
    }
  }
  getBookByStorage(storageId:number, count:number, size:number){
    this.productService.getBookByStorage(storageId, count, size).subscribe((res:any)=> {
      this.datasBook = res;
    });
    this.productService.getCountBookByStorage(this.data.message).subscribe((res:any) => {
      this.totalPages = Math.ceil(res/7);
    })
  }
  clickMore(){
    if (this.count < this.totalPages - 1){
      this.count += 1;
      if(this.data.message1 != undefined){
        this.productService.getBookByStorageAndRequest(this.data.message, this.data.message1, this.count, 7).subscribe((res:any)=>{
          this.datasBook = res
        })
      }
      else if(this.isCheckBookRemainsZero == true){
        this.productService.getBookRemainsZero(this.data.message, this.count, 7).subscribe((res:any) => {
          this.datasBook = res;
        });
      }
      else {
        this.productService.getBookByStorage(this.data.message, this.count, 7).subscribe((res:any)=> {
          this.datasBook = res
        })
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      if(this.data.message1 != undefined){
        this.productService.getBookByStorageAndRequest(this.data.message, this.data.message1, this.count, 7).subscribe((res:any)=>{
          this.datasBook = res
        })
      }
      else if(this.isCheckBookRemainsZero == true){
        this.productService.getBookRemainsZero(this.data.message, this.count, 7).subscribe((res:any) => {
          this.datasBook = res;
        });
      }
      else {
        this.productService.getBookByStorage(this.data.message, this.count, 7).subscribe((res:any)=> {
          this.datasBook = res
        })
      }
    }
  }
  clickSearch(){
    this.count = 0;
    this.productService.getBookByStorageAndRequest(this.data.message, this.inputValue, this.count, 7).subscribe((res:any)=>{
      this.datasBook = res;
    });
    this.productService.getCountAllBookByStorageAndRequest(this.data.message, this.inputValue).subscribe((res:any) => {
      this.totalPages = Math.ceil(res/7);
    })
  }
  sendAddBook(){
    let data = { message: this.data.message};
    this.router.navigate(['/addBook', data]);
  }
  changeLogout(){
    return{
      'display': this.logOut ? 'block' : 'none'
    }
  }

  clickUser(){
    this.logOut = !this.logOut;
  }
  src = '';
  isClickImage = false
  clickImage(src:string){
    this.src = src;
    this.isClickImage = !this.isClickImage;
  }

  changeBigImage(){
    return{
      'display':this.isClickImage?'block':'none',
      'height':this.isClickImage?'30rem':'0rem',
    }
  }

  clickLogout(){
    this.valueSave.clear();
  }
  changeBookAndQuantity(){
    let quantity = this.book?.quantity?.toString();
    const url = 'http://localhost:8080/bookStorage/updateBookStorage'
    if(Number(this.changeQuantity) != null && this.changeQuantity != '' && this.book?.quantity != undefined){
      if(this.book.quantity == 0){
        quantity = this.changeQuantity;
      }else {
        quantity = (Number(this.changeQuantity) + this.book.quantity ?? 0).toString();
      }
    }
    let bookCheck:Book[]=[]
    const body = {bookStorageId:this.book?.id, image:this.inputImage, quantity: quantity, accountId: this.admin?.cardNumber}
    this.productService.postData(url, body).subscribe((res:any)=>{
      bookCheck = res;
      if (bookCheck.length > 0){
        this.isCheckSave = true;
        this.mess = 'Sửa thành công sách: '+this.book?.id;
      }
      else this.mess = 'Sửa không thành công kiểm tra lại thông tin';
      this.isMess = true;
      this.changeBook = false;
      this.isSetBook = false
      this.display();
    })
  }
  timeNow = '';
  getTimeNow(){
    const now = DateTime.now();
    const nowInVietnamTimezone = now.setZone('Asia/Ho_Chi_Minh');
    this.timeNow = nowInVietnamTimezone.toFormat('yyyy-MM-dd HH:mm:ss');
  }
  clickChangeBook(){
    return{
      'display':this.changeBook?'block':'none',
    }
  }
  isCheckSave = false;
  mess = '';
  displayMess(){
    return{
      'display':this.isMess?'block':'none',
    }
  }
  isSetBook = false;
  category:string = '';
  author:string = '';
  isMoreSelectAuthor = false;
  status = 'Trạng thái';
  dataStatus:string[]=[];
  title:string = '';
  content:string = '';
  cost:string = '';
  sale:string = '';
  getAllStatus(){
    this.productService.getAllStatus().subscribe((res:any) => {
      this.dataStatus = res
    })
  }
  optionStatus(event: any) {
    this.status = event.target.value;
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
  clickSelectCategory(string:string){
    this.category = string;
  }
  changeSetBook(){
    return{
      'display':this.isSetBook ? 'block':'none'
    }
  }

  isMoreSelectCategory = false;

  clickMoreSelectCategory(){
    this.isMoreSelectCategory = !this.isMoreSelectCategory;
  }
  changeMoreSelectCategory(){
    return{
      'display': this.isMoreSelectCategory ? 'block':'none',
    }
  }
  changeHeight(){
    return{
      'height': this.isMoreSelectCategory ? '28%':'10%',
    }
  }
  addNewBook = false
  changeAddNewBook(){
    return{
      'display':this.addNewBook ? 'block':'none',
    }
  }
  clickAdd(){
    const url = 'http://localhost:8080/storage/addBookOnStorage/'+this.data.message+'/'+this.admin?.cardNumber+'/'+this.count+'/7';
    if(this.title != '' && this.category != '' && this.author != '' && this.inputImage != '' && this.content != '' && this.cost != '' && this.changeQuantity != ''){
      this.isCheckSave = true;
      const body = {title:this.title, category:this.category, author: this.author, image: this.inputImage, content: this.content, cost: this.cost, quantity: this.changeQuantity}
      this.productService.postData(url, body).subscribe((res:any) => {
        this.datasBook = res;
      })
    }
    if (this.isCheckSave == true){
      this.mess = 'Thêm sách '+this.title+' thành công';
      this.isCheckSave = false;
    }
    else this.mess = 'Thêm sách không thành công kiểm tra lại thông tin';
    this.isMess = true;
    this.changeBook = false;
    this.addNewBook = false;
    this.title = '';
    this.category = '';
    this.author = '';
    this.inputImage = '';
    this.content = '';
    this.cost = '';
    this.changeQuantity = '';
    this.display();
  }
  checkSelect = false;
  selectBookStorage(){
    this.count = 0;
    let category = '';
    let author = '';
    const url = 'http://localhost:8080/book/selectBookInStorage/' + this.count + '/7';
    if(this.categoryName == 'Tất cả thể loại') category = '';
    else category = this.categoryName;
    if(this.authorName == 'Tất cả tác giả') author = '';
    else author = this.authorName;
    const body = {storageId:this.data.message, category:category, author: author, timeStart: this.timeStart
      , timeEnd: this.timeEnd, quantityStart: this.min, quantityEnd: this.max}
    this.productService.postData(url, body).subscribe((res:any) => {
      this.datasBook = res;
      this.checkSelect = true;
      this.isClickCategoryMore = false;
      this.isClickAuthorMore = false;
      this.isClickImportTimeMore = false;
      this.isClickQuantityMore = false;
    })
    this.productService.postData('http://localhost:8080/book/countSelectBookInStorage', body).subscribe((res:any) => {
      this.totalPages = Math.ceil(res/7);
    })
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

  isCheckBookRemainsZero = false;

  getBookRemainsZero(){
    this.isCheckBookRemainsZero = true;
    this.productService.getBookRemainsZero(this.data.message, 0, 7).subscribe((res:any) => {
      this.datasBook = res;
    });
    this.productService.getCountBookRemainsZero(this.data.message).subscribe((res:any) => {
      this.totalPages = Math.ceil(res/7);
    });
  }

  selectPage(page: number) {
    this.count = page - 1;
    if(this.data.message1 != undefined){
      this.productService.getBookByStorageAndRequest(this.data.message, this.data.message1, this.count, 7).subscribe((res:any)=>{
        this.datasBook = res
      })
    }
    else {
      this.productService.getBookByStorage(this.data.message, this.count, 7).subscribe((res:any)=> {
        this.datasBook = res
      })
    }
  }
}
