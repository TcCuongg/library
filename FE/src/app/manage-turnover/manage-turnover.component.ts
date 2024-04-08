import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute} from "@angular/router";
import {Account, BookPay} from "../app.module";

@Component({
  selector: 'app-manage-turnover',
  templateUrl: './manage-turnover.component.html',
  styleUrls: ['./manage-turnover.component.css']
})
export class ManageTurnoverComponent implements OnInit{
  count = 0;
  inputValue = '';
  valueSave:any;
  admin:Account|undefined;
  datasBookPay:BookPay[]=[];
  dataCategory:string[]=[];
  categoryName = "Tất cả thể loại";
  timeStart = '';
  timeEnd = '';
  cost = 0;

  isSetUser = false;
  userName = '';
  userEmail = '';
  userPhone = '';
  userAddress = '';
  passOld = '';
  passNew = '';


  logOut = false;
  isCheckSearch = false;
  isCheckSelection = false;
  isClickCategoryMore = false;
  constructor(private productService: ProductService,
              private route: ActivatedRoute) {
  }
  ngOnInit() {
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem("value"));
    this.getAllBookPay(this.count);
    this.getAllCategoryName();
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

  selectCategory(categoryName:string){
    this.categoryName = categoryName;
    this.clickCategoryMore();
  }
  changeCategoryMore(){
    return{
      'display':this.isClickCategoryMore ? 'block':'none'
    }
  }
  clickCategoryMore(){
    this.isClickCategoryMore = !this.isClickCategoryMore;
  }
  getAllCategoryName(){
    this.productService.getAllCategoryName().subscribe((res:any) => {
      this.dataCategory = res
    })
  }
  getAllBookPay(count:number){
    this.productService.getBookPayByDelivered(count, 11).subscribe((res:any) => {
      this.datasBookPay = res;
      this.productService.getCountBookPayByDelivered().subscribe((res:any) => {
        this.totalPages = Math.ceil(res/11);
      })
    })
  }
  clickSearch(){
    this.count = 0;
    this.cost = 0;
    this.isCheckSearch = true;
    this.productService.getBookPayByDeliveredRequest(this.inputValue, this.count, 11).subscribe((res:any) => {
      this.datasBookPay = res;
      if(this.datasBookPay.length > 0){
        for(let item of this.datasBookPay){
          this.cost += Number(item.buyCost);
        }
      }
      this.productService.getCountBookPayByDeliveredRequest(this.inputValue).subscribe((res:any) => {
        this.totalPages = Math.ceil(res/11);
      })
    })
  }
  clickSelection(){
    this.count = 0;
    this.isCheckSelection = true;
    this.isCheckSearch = false;
    let category = '';
    if(this.categoryName != "Tất cả thể loại") category = this.categoryName;
    const body = {category: category, timeStart: this.timeStart, timeEnd: this.timeEnd};
    this.productService.postData('http://localhost:8080/book/selectionBookPayByTurnover/' + this.count + '/11', body).subscribe((res:any) => {
      this.datasBookPay = res;
      this.productService.postData('http://localhost:8080/book/sumBookPayByTurnover', body).subscribe((res:any) => {
        this.cost = res;
        if(this.cost == undefined || this.cost == null){
          this.cost = 0;
        }
      })
      this.productService.postData('http://localhost:8080/book/countSelectionBookPayByTurnover', body).subscribe((res:any) => {
        this.totalPages = Math.ceil(res/11);
      })
    })
  }
  clickMore(){
    if(this.count < this.totalPages - 1){
      this.count += 1;
      if(this.isCheckSelection == true){
        let category = '';
        if(this.categoryName != "Tất cả thể loại") category = this.categoryName;
        const body = {category: category, timeStart: this.timeStart, timeEnd: this.timeEnd};
        this.productService.postData('http://localhost:8080/book/selectionBookPayByTurnover/' + this.count + '/11', body).subscribe((res:any) => {
          this.datasBookPay = res;
          this.productService.postData('http://localhost:8080/book/sumBookPayByTurnover', body).subscribe((res:any) => {
            this.cost = res;
          })
        })
      }else {
        this.productService.getBookPayByDelivered(this.count, 11).subscribe((res:any) => {
          this.datasBookPay = res;
        })
      }
    }
  }
  clickLast(){
    if(this.count - 1 >= 0){
      this.count -= 1;
      if(this.isCheckSelection == true){
        let category = '';
        if(this.categoryName != "Tất cả thể loại") category = this.categoryName;
        const body = {category: category, timeStart: this.timeStart, timeEnd: this.timeEnd};
        this.productService.postData('http://localhost:8080/book/selectionBookPayByTurnover/' + this.count + '/11', body).subscribe((res:any) => {
          this.datasBookPay = res;
          this.productService.postData('http://localhost:8080/book/sumBookPayByTurnover', body).subscribe((res:any) => {
            this.cost = res;
          })
        })
      }else {
        this.productService.getBookPayByDelivered(this.count, 11).subscribe((res:any) => {
          this.datasBookPay = res;
        })
      }
    }
  }

  clickUser(){
    this.logOut = !this.logOut;
  }
  changeLogout(){
    return{
      'display': this.logOut ? 'block' : 'none',
      'z-index': this.logOut ? '9':'1',
    }
  }
  clickLogout(){
    this.valueSave.clear();
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
    if(this.isCheckSelection == true){
      let category = '';
      if(this.categoryName != "Tất cả thể loại") category = this.categoryName;
      const body = {category: category, timeStart: this.timeStart, timeEnd: this.timeEnd};
      this.productService.postData('http://localhost:8080/book/selectionBookPayByTurnover/' + this.count + '/11', body).subscribe((res:any) => {
        this.datasBookPay = res;
        this.productService.postData('http://localhost:8080/book/sumBookPayByTurnover', body).subscribe((res:any) => {
          this.cost = res;
        })
      })
    }else {
      this.productService.getBookPayByDelivered(this.count, 11).subscribe((res:any) => {
        this.datasBookPay = res;
      })
    }
  }
}
