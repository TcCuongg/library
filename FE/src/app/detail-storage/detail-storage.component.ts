import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Account, Book} from "../app.module";
import {DatePipe} from "@angular/common";

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
  inputValue='';
  book:Book | undefined;

  valueSave:any;


  admin:Account | undefined;

  logOut = false;
  inputImage='';
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
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
  }

  clickBook(book:Book){
    this.book = book;
    let datePipe = new DatePipe('en-US');
    this.inputImage=this.book.image ?? '';
    this.inputImportTime = (datePipe.transform(this.book.importTime, 'yyyy-MM-dd HH:mm:ss') ?? '').toString();
    this.inputQuantity=(this.book.quantity ?? '').toString();
  }

  display(){
    if(this.data.message1 != undefined){
      this.getBookByStorage(this.data.message, this.count, 9);
      this.productService.getBookByStorageAndRequest(this.data.message, this.data.message1, this.count, 9).subscribe((res:any)=>{
        this.datasBook = res
      })
    }
    else {
      this.getBookByStorage(this.data.message, this.count, 9);
    }
  }
  getBookByStorage(storageId:number, count:number, size:number){
    this.productService.getBookByStorage(storageId, count, size).subscribe((res:any)=> {
      this.datasBook = res
    })
  }
  clickMore(){
    if (this.datasBook.length==9){
      this.count = this.count + 1;
      this.getBookByStorage(this.data.message, this.count, 9);
      if(this.datasCheckBook.length != 0){
        this.datasBook = this.datasCheckBook;
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      this.getBookByStorage(this.data.message, this.count, 9);
    }
  }
  clickSearch(){
    this.count = 0;
    this.productService.getBookByStorageAndRequest(this.data.message, this.inputValue, this.count, 8).subscribe((res:any)=>{
      this.datasBook = res
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

  clickLogout(){
    this.valueSave.clear();
  }
  changeBookAndQuantity(){
    const url = 'http://localhost:8080/bookStorage/updateBookStorage';
    const body = {bookStorageId:this.book?.id, image: this.inputImage, importTime: this.inputImportTime, quantity:this.inputQuantity}
    this.productService.postData(url, body).subscribe();
    this.display();
  }
}
