import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute} from "@angular/router";
import {Account, BookPay} from "../app.module";

@Component({
  selector: 'app-manage-pay',
  templateUrl: './manage-pay.component.html',
  styleUrls: ['./manage-pay.component.css']
})
export class ManagePayComponent implements OnInit{
  valueSave:any;
  count = 0;
  datasBookPay:BookPay[]=[];
  bookPay:BookPay|undefined;
  admin:Account | undefined;

  isSetUser = false;
  userName = '';
  userEmail = '';
  userPhone = '';
  userAddress = '';
  passOld = '';
  passNew = '';

  inputValue = '';

  logOut = false;

  buyStatus = 'Đơn hàng đang được người bán chuẩn bị';
  listIndex = [true, false, false];
  isType1 = true;
  isType2 = false;
  isCheckSearch = false;

  constructor(private productService: ProductService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem("value"));
    this.getAllBookPay(this.count);
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

  clickOk(bookPay:BookPay){
    this.mess = 'Chắc chắn đơn hàng đã giao nhận thành công';
    this.isMess = true;
    this.isConfirm = true;
    this.bookPay = bookPay;
    this.buyStatus = 'Đơn hàng đã giao thành công'
  }
  clickBom(bookPay:BookPay){
    this.mess = 'Chắc chắn đơn hàng giao thất bại và hàng đã trở về trong kho';
    this.isMess = true;
    this.isConfirm = true;
    this.bookPay = bookPay;
    this.buyStatus = 'Đơn hàng Bom';
  }
  isConfirm = false;
  confirmBook(){
    const body = {accountId: this.admin?.cardNumber, buyId: this.bookPay?.buyId, status: this.buyStatus}
    this.productService.postData('http://localhost:8080/buy/updateBuyOk/' + this.count + '/11', body).subscribe((res:any) => {
      this.datasBookPay = res;
      this.productService.getCountBookPayByDelivering().subscribe((res:any) => {
        this.totalPages = Math.ceil(res/11);
      })
    });
  }
  clickPass(bookPay:BookPay){
    const body = {accountId: this.admin?.cardNumber, buyId: bookPay.buyId, status: "Đơn hàng đang trên đường giao"};
    let dataCheck:BookPay;
    this.productService.postData('http://localhost:8080/buy/updateBuy', body).subscribe((res:any) => {
      dataCheck = res;
      if(dataCheck.buyStatus == "Hết hàng") this.mess = 'Duyệt không thành công hàng trong kho không đủ';
      else if(dataCheck.buyStatus == "Tài khoản mua hàng đã bị khóa") this.mess = 'Duyệt không thành công tài khoản mua hàng đã bị khóa';
      else this.mess = 'Duyệt thành công đơn hàng';
      this.isMess = true;
      this.isConfirm = false;
      this.getAllBookPay(this.count);
    })
  }
  getAllBookPay(count:number){
    this.productService.getAllBookPay(count, 11).subscribe((res:any) => {
      this.datasBookPay = res;
    })
    this.productService.getCountAllBookPay().subscribe((res:any) => {
      this.totalPages = Math.ceil(res/11);
    })
  }
  getBookPayByDelivering(){
    this.count = 0;
    this.productService.getBookPayByDelivering(this.count, 11).subscribe((res:any) => {
      this.datasBookPay = res;
    })
    this.productService.getCountBookPayByDelivering().subscribe((res:any) => {
      this.totalPages = Math.ceil(res/11);
    })
  }
  getBookPayByBom(){
    this.count = 0;
    this.productService.getBookPayByBom(this.count, 11).subscribe((res:any) => {
      this.datasBookPay = res;
    })
    this.productService.getCountBookPayByBom().subscribe((res:any) => {
      this.totalPages = Math.ceil(res/11);
    })
  }

  clickBuyStatus(string:string, index:number){
    this.buyStatus = string;
    this.listIndex = [false, false, false];
    this.listIndex[index] = true;
    if(index == 0){
      this.isType1 = true;
      this.isType2 = false
    }
    else {
      this.isType1 = false;
      this.isType2 = true;
    }
  }
  changeBuyStatus(index:number){
    return{
      'color':this.listIndex[index] ? '#590009':'#b65313',
    }
  }
  changeTable(){
    return{
      'display':this.isType2 ? 'table-cell':'none',
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
    this.isConfirm = false;
    this.isMess = !this.isMess;
    this.mess = '';
  }
  clickSearch(){
    this.count = 0;
    this.isCheckSearch = true;
    for(let i = 0; i < 3; i++){
      if(this.listIndex[i] == true){
        this.productService.getBookPayByRequest(this.inputValue, i, this.count, 11).subscribe((res:any) => {
          this.datasBookPay = res;
          this.productService.getCountBookPayByRequest(this.inputValue, i).subscribe((res:any) => {
            this.totalPages = Math.ceil(res/11);
          })
        })
      }
    }
  }
  clickMore(){
    if(this.count < this.totalPages - 1){
      this.count += 1;
      if(this.isCheckSearch = true){
        for(let i = 0; i < 3; i++){
          if(this.listIndex[i] == true){
            this.productService.getBookPayByRequest(this.inputValue, i, this.count, 11).subscribe((res:any) => {
              this.datasBookPay = res;
              this.productService.getCountBookPayByRequest(this.inputValue, i).subscribe((res:any) => {
                this.totalPages = Math.ceil(res/11);
              })
            })
          }
        }
      }
      else {
        this.productService.getAllBookPay(this.count, 11).subscribe((res:any) => {
          this.datasBookPay = res;
        })
      }
    }
  }
  clickLast(){
    if(this.count - 1 >= 0){
      this.count -= 1;
      if(this.isCheckSearch = true){
        for(let i = 0; i < 3; i++){
          if(this.listIndex[i] == true){
            this.productService.getBookPayByRequest(this.inputValue, i, this.count, 11).subscribe((res:any) => {
              this.datasBookPay = res;
              this.productService.getCountBookPayByRequest(this.inputValue, i).subscribe((res:any) => {
                this.totalPages = Math.ceil(res/11);
              })
            })
          }
        }
      }
      else {
        this.productService.getAllBookPay(this.count, 11).subscribe((res:any) => {
          this.datasBookPay = res;
        })
      }
    }
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
    this.productService.getAllBookPay(this.count, 11).subscribe((res:any) => {
      this.datasBookPay = res;
    })
  }
}
