import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Account, Book} from "../app.module";

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit{
  inputValue='';
  data: any;
  datasBook:Book[]=[];

  valueSave:any;


  admin:Account | undefined;

  logOut = false;

  listInputValue = [{title:'', category:'', author:'', image:'', content:'', cost:'', quantity:''},
    {title:'', category:'', author:'', image:'', content:'', cost:'', quantity:''},
    {title:'', category:'', author:'', image:'', content:'', cost:'', quantity:''},
    {title:'', category:'', author:'', image:'', content:'', cost:'', quantity:''},
    {title:'', category:'', author:'', image:'', content:'', cost:'', quantity:''},
    {title:'', category:'', author:'', image:'', content:'', cost:'', quantity:''},
    {title:'', category:'', author:'', image:'', content:'', cost:'', quantity:''},
    {title:'', category:'', author:'', image:'', content:'', cost:'', quantity:''}];

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) { }
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.data = params;
    });
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
  }
  sendInputValue(){
    let data = { message: this.data.message, message1: this.inputValue};
    this.router.navigate(['/detailStorage', data]);
  }
  changeLogout(){
    return{
      'display': this.logOut ? 'block' : 'none'
    }
  }

  backDetailStorage(){
    let data = { message: this.data.message};
    this.router.navigate(['/detailStorage', data]);
  }

  clickUser(){
    this.logOut = !this.logOut;
  }

  clickLogout(){
    this.valueSave.clear();
  }

  list:any[]=[];
  isCheck = false;

  changeListAdd(){
    return{
      'color': this.isCheck ? 'red' : 'black'
    }
  }
  saveBook(){
    this.isCheck = false;
    for (let item of this.listInputValue){
      if(item != undefined){
        this.list.push(item);
      }
    }
    if(this.list.length != 0){
      const url = 'http://localhost:8080/storage/addBookOnStorage/' + this.data.message
      const body = this.list;
      this.productService.postData(url, body).subscribe((res:any)=>{
        this.datasBook = res
      });
      if(this.datasBook.length == 0){
        this.isCheck = true;
      }
      else {
        for (let item of this.listInputValue){
          item.title = '';
          item.category = '';
          item.author = '';
          item.image = '';
          item.content = '';
          item.cost = '';
          item.quantity = '';
        }
      }
    }
  }
}
