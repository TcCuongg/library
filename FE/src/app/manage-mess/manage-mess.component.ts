import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {Account, Mess} from "../app.module";
import {flush} from "@angular/core/testing";

@Component({
  selector: 'app-manage-mess',
  templateUrl: './manage-mess.component.html',
  styleUrls: ['./manage-mess.component.css']
})
export class ManageMessComponent implements OnInit{
  datasMess:Mess[]=[];
  datasCheckMess:Mess[]=[];
  count= 0;
  inputValue:string = '';
  valueTitle:string = '';
  valueContent:string = '';

  isSetUser = false;
  userName = '';
  userEmail = '';
  userPhone = '';
  userAddress = '';
  passOld = '';
  passNew = '';

  valueSave:any;


  admin:Account | undefined;

  logOut = false;
  isClickSend = false;
  isCheck = false;
  constructor(private productService: ProductService) {}
  ngOnInit() {
    this.getAllMess(this.count, 11);
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
  getAllMess(count:number, step:number){
    if(this.isCheck == true){
      const url = 'http://localhost:8080/account/findMessByTimeSent/'+count+'/'+step;
      const body = {start: this.start, end: this.end, status: ''};
      this.productService.postData(url, body).subscribe((res:any) => {
        this.datasMess = res;
      })
    }else if(this.isCheckSearch == true){
      this.productService.getAllMessByRequest(this.inputValue, this.count, 11).subscribe((res:any)=>{
        this.datasMess = res;
      })
    }
    else {
      this.productService.getAllMess(count, step).subscribe((res:any)=>{
        this.datasMess = res;
      });
      this.productService.getCountAllMess().subscribe((res:any) => {
        this.totalPages = Math.ceil(res/11);
      });
    }
  }
  clickMore(){
    if (this.count < this.totalPages - 1){
      this.count += 1;
      if(this.isCheck == true){
        const url = 'http://localhost:8080/account/findMessByTimeSent/'+this.count+'/'+11;
        const body = {start: this.start, end: this.end, status: ''};
        this.productService.postData(url, body).subscribe((res:any) => {
          this.datasMess = res;
        })
      }else if(this.isCheckSearch == true){
        this.productService.getAllMessByRequest(this.inputValue, this.count, 11).subscribe((res:any)=>{
          this.datasMess = res;
        })
      }
      else {
        this.productService.getAllMess(this.count, 11).subscribe((res:any)=>{
          this.datasMess = res;
        })
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      if(this.isCheck == true){
        const url = 'http://localhost:8080/account/findMessByTimeSent/'+this.count+'/'+11;
        const body = {start: this.start, end: this.end, status: ''};
        this.productService.postData(url, body).subscribe((res:any) => {
          this.datasMess = res;
        })
      }else if(this.isCheckSearch == true){
        this.productService.getAllMessByRequest(this.inputValue, this.count, 11).subscribe((res:any)=>{
          this.datasMess = res;
        })
      }
      else {
        this.productService.getAllMess(this.count, 11).subscribe((res:any)=>{
          this.datasMess = res;
        })
      }
    }
  }
  isCheckSearch = false;
  clickSearch(){
    if(this.inputValue != ''){
      this.isCheckSearch = true;
      this.count = 0;
      this.productService.getAllMessByRequest(this.inputValue, this.count, 11).subscribe((res:any)=>{
        this.datasMess = res;
        this.productService.getCountAllMessByRequest(this.inputValue).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/11);
        })
      })
    }
  }
  clickSend(){
    this.isClickSend = !this.isClickSend;
  }
  changeSend(){
    return{
      'display': this.isClickSend ? 'block' : 'none'
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
  sentNewMess(){
    const url = 'http://localhost:8080/account/addNewMess';
    const body = {title: this.valueTitle, content: this.valueContent}
    let countNewMess;
    this.productService.postData(url, body).subscribe((res:any)=>{
      countNewMess = res;
      if(countNewMess != 0){
        this.mess = "Thêm thành công và gửi thông báo tới " + countNewMess + " tài khoản";
      }
      else this.mess = "Thêm không thành công vui lòng kiểm tra lại các trường";
      this.isMess = true;
      this.getAllMess(this.count, 11);
    });
  }
  clickManageMess(){
    this.count = 0;
    this.getAllMess(this.count, 11);
  }
  isSetting:any[]=[{status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}, {status: false}]
  changeText(index: number) {
    this.isSetting[index].status = !this.isSetting[index].status;
  }
  changWeb(){
    return{
      'opacity':this.isClickSend ? '0.2':'1',
    }
  }
  start = '';
  end = '';
  selectMess(){
    if(this.start != '' || this.end != ''){
      this.count = 0;
      this.isCheck = true;
      const url = 'http://localhost:8080/account/findMessByTimeSent/'+this.count+'/11';
      const body = {start: this.start, end: this.end, status: ''};
      this.productService.postData(url, body).subscribe((res:any) => {
        this.datasMess = res;
        this.productService.postData('http://localhost:8080/account/getCountAllMessBySent', body).subscribe((res:any) => {
          this.totalPages = Math.ceil(res/11);
        })
      })
    }
  }
  isMess = false;
  mess = '';
  changeMess(){
    return{
      'display':this.isMess ? 'block':'none',
    }
  }
  clickCloseMess(){
    this.isMess = false;
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
    if(this.isCheck == true){
      const url = 'http://localhost:8080/account/findMessByTimeSent/'+this.count+'/'+11;
      const body = {start: this.start, end: this.end, status: ''};
      this.productService.postData(url, body).subscribe((res:any) => {
        this.datasMess = res;
      })
    }else if(this.isCheckSearch == true){
      this.productService.getAllMessByRequest(this.inputValue, this.count, 11).subscribe((res:any)=>{
        this.datasMess = res;
      })
    }
    else {
      this.productService.getAllMess(this.count, 11).subscribe((res:any)=>{
        this.datasMess = res;
      })
    }
  }
}
