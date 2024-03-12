import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ProductService} from "../product.service";
import {Account, Category, Storage} from "../app.module";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manage-style',
  templateUrl: './manage-style.component.html',
  styleUrls: ['./manage-style.component.css']
})
export class ManageStyleComponent implements OnInit{
  @ViewChildren('input') listInput: QueryList<ElementRef> | undefined;
  dataCategory:Category[]=[];
  inputName = '';
  inputSale = '';
  inputSearch = '';
  count = 0;
  dataCheckCategory:Category[]=[];
  category: Category | undefined;

  valueSave:any;


  admin:Account | undefined;

  isLogin2 = true;
  logOut = false;

  isClickMore = false;

  constructor(private productService:ProductService,
              private router: Router) {
  }
  ngOnInit() {
    this.getAllCategory(this.count);
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
  }

  getAllCategory(count:number){
    this.productService.getCategory(count, 4).subscribe((res:any) => {
      this.dataCategory = res
    })
  }

  clickSearch(){
    this.productService.getCategoryByName(this.inputSearch, this.count, 4).subscribe((res:any) => {
      this.dataCategory = res
    })
  }

  sendContentCategory(category:Category){
    let data = { messageFromManageStyle: category.name};
    this.router.navigate(['/manageBook', data]);
  }

  updateCategory(category:Category, input:string){
    if(input != ''){
      const url = 'http://localhost:8080/category/updateCategorySale';
      const body = {id: category.id, sale: Number(input)}
      this.productService.putData(url, body).subscribe((res:any) => {
        this.category = res;
      });
    }
  }

  saveNewCategory(){
    if(this.inputName != '' && this.inputSale != ''){
      const url = 'http://localhost:8080/category/addNewCategory';
      const body = {id: 0, name: this.inputName, sale: Number(this.inputSale)}
      this.productService.postData(url, body).subscribe((res:any) => {
        this.category = res;
      });
      this.productService.getCategory(this.count, 4).subscribe((res:any) => {
        this.dataCategory = res
      })
    }
  }

  clickMorefirst(){
    if (this.dataCategory.length==4){
      this.count = this.count + 1;
      this.productService.getCategory(this.count, 4).subscribe((res:any) => {
        this.dataCheckCategory = res
      })
      if(this.dataCheckCategory.length != 0){
        this.dataCategory = this.dataCheckCategory;
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      this.productService.getCategory(this.count, 4).subscribe((res:any) => {
        this.dataCategory = res
      })
    }
  }

  clickMore(){
    this.isClickMore = !this.isClickMore;
  }
  changeMoreStorage(){
    return{
      'display': this.isClickMore ? 'block' : 'none',
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
  clickManageStyle(){
    this.count = 0;
    this.getAllCategory(this.count);
  }
}
