import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProductService} from "../product.service";
import {Account} from "../app.module";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.css']
})
export class ManageUserComponent implements OnInit {
  datasAccount:Account[]=[];
  datasCheckAccuont:Account[]=[];
  getAccountNew:Account[]=[];
  account: Account | undefined;
  accountStatus:string[] = [];
  count = 0;
  inputValue: string = '';
  inputName = '';
  inputEmail = '';
  inputAddress = '';
  inputLevel = '';
  inputStatus = '';
  inputPhone = '';
  inputPass = '';
  start = '';
  end = '';
  status = 'Tất cả trạng thái';
  typeAccount = 'user';
  statusAccount = 'opend';
  mess = '';

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
  isMoreStatus = false;
  isMess = false;
  isNewAccount = false;
  isSetAccount = false;
  buttonNew = false;
  isCheckSetAccount = false;
  isCheckClose = false;
  isCheckSelect = false;
  isCheckRequest = false;

  constructor(private productService: ProductService) {}
  ngOnInit() {
    this.getAll(this.count, 11);
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
    this.getAllAccountStatus();
    this.getAllCountPage();
  }
  // clickNewAccount(){
  //   this.isNewAccount = !this.isNewAccount;
  // }

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

  changeNewAccount(){
    return{
      'display':this.isNewAccount ? 'block':'none',
    }
  }
  clickCloseMess(){
    this.isMess = false;
    this.mess = '';
  }
  changeMess(){
    return{
      'display':this.isMess ? 'block':'none',
    }
  }
  changeTypeAccount(){
    if(this.typeAccount == 'user') this.typeAccount = 'admin';
    else this.typeAccount = 'user';
  }
  changeStatusAccount(){
    if(this.statusAccount == 'close') this.statusAccount = 'opend';
    else this.statusAccount = 'close';
  }
  getAllAccountStatus(){
    this.productService.getAllAccountStatus().subscribe((res:any)=>{
      this.accountStatus = res;
    })
  }
  getAll(count:number, step:number){
    if(this.isCheckSelect == true){
      let status = '';
      const url = 'http://localhost:8080/account/findAccountByTimeCreate/'+count+'/'+step;
      if(this.status != 'Tất cả trạng thái') status = this.status;
      if(this.status != 'Tất cả trạng thái' || this.start != '' ||this.end != ''){
        const body = {start: this.start, end: this.end, status: status}
        this.productService.postData(url, body).subscribe((res:any)=>{
          this.datasAccount = res;
        });
      }
    }else {
      this.productService.getAllAccount(count, step).subscribe((res:any)=>{
        this.datasAccount = res
      })
    }
  }

  clickAccount(account:Account){
    this.account = account;
    this.inputName = this.account.name ?? '';
    this.inputEmail = this.account.email ?? '';
    this.inputPhone = (this.account.phone ?? 0).toString();
    this.inputAddress = this.account.address ?? '';
    this.inputLevel = (this.account.level ?? 0).toString();
    this.inputStatus = this.account.status ?? '';
  }

  changeAccount(){
    const url = 'http://localhost:8080/account/updateAccount/'+this.count+'/11';
    if(this.inputName != '' && this.inputEmail != '' && this.inputPhone != '' && this.inputAddress != '' && this.inputLevel != '' && this.inputStatus != ''){
      this.mess = 'Sửa thông tin thành công';
      const body = {cardNumber: this.account?.cardNumber, name: this.inputName, email: this.inputEmail, phone: this.inputPhone, address: this.inputAddress, level: this.inputLevel, status: this.inputStatus}
      this.productService.postData(url, body).subscribe((res:any)=>{
        this.datasAccount = res;
      });
    }else this.mess = 'Sửa thông tin thất bại';
    this.resets();
    this.isMess = true;
  }

  resets(){
    this.inputName = '';
    this.inputEmail = '';
    this.inputPhone = '';
    this.inputAddress = '';
    this.typeAccount = 'user';
    this.inputPass = '';
  }

  addNewAccount(){
    let getAccountNew:Account[]=[];
    this.getAccountNew = getAccountNew;
    const url = 'http://localhost:8080/account/addNewAccount'
    const body = {username: this.inputName, email: this.inputEmail, phone: this.inputPhone, address: this.inputAddress, password: this.inputPass, type: this.typeAccount}
    this.productService.postData(url, body).subscribe((res:any)=>{
      this.getAccountNew = res;
      if(this.getAccountNew.length > 0){
        this.mess = 'Đăng ký tài khoản thành công';
      }else this.mess = 'Đăng ký không thành công vui lòng kiểm tra lại thông tin';
    });
    this.isMess = true;
    this.getAll(this.count, 11);
  }


  selectAccount(){
    this.count = 0;
    let status = '';
    const url = 'http://localhost:8080/account/findAccountByTimeCreate/'+this.count+'/11';
    const url2 = 'http://localhost:8080/account/getCountSelectAccount';
    if(this.status != 'Tất cả trạng thái') status = this.status;
    if(this.status != 'Tất cả trạng thái' || this.start != '' ||this.end != ''){
      this.isCheckSelect = true;
      this.isCheckRequest = false
      const body = {start: this.start, end: this.end, status: status}
      this.productService.postData(url, body).subscribe((res:any)=>{
        this.datasAccount = res;
      });
      this.productService.postData('http://localhost:8080/account/getCountSelectAccount', body).subscribe((res:any) => {
        this.totalPages = this.totalPages = Math.ceil(res/11);
      })
    }
  }

  selectStatus(status:string){
    this.status = status;
  }

  clickMoreStatus(){
    this.isMoreStatus = !this.isMoreStatus;
  }

  changeMoreStatus(){
    return{
      'display':this.isMoreStatus ? 'block':'none',
    }
  }

  clickSearch(){
    this.count = 0;
    if(this.inputValue != ''){
      this.isCheckRequest = true;
      this.isCheckSelect = false;
      this.productService.getSearchAccount(this.inputValue, this.count, 11).subscribe((res:any)=>{
        this.datasAccount = res
      })
    }
  }
  clickMore(){
    if (this.count < this.totalPages - 1){
      this.count += 1;
      if(this.isCheckRequest == true){
        this.productService.getSearchAccount(this.inputValue, this.count, 11).subscribe((res:any)=>{
          this.datasAccount = res
        })
      }
      else if(this.isCheckSelect == true){
        let status = '';
        const url = 'http://localhost:8080/account/findAccountByTimeCreate/'+this.count+'/11';
        if(this.status != 'Tất cả trạng thái') status = this.status;
        const body = {start: this.start, end: this.end, status: status}
        this.productService.postData(url, body).subscribe((res:any)=>{
          this.datasAccount = res;
        });
      }
      else {
        this.productService.getAllAccount(this.count, 11).subscribe((res:any)=>{
          this.datasAccount = res
        })
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      if(this.isCheckRequest == true){
        this.productService.getSearchAccount(this.inputValue, this.count, 11).subscribe((res:any)=>{
          this.datasAccount = res
        })
      }
      else if(this.isCheckSelect == true){
        let status = '';
        const url = 'http://localhost:8080/account/findAccountByTimeCreate/'+this.count+'/11';
        if(this.status != 'Tất cả trạng thái') status = this.status;
        const body = {start: this.start, end: this.end, status: status}
        this.productService.postData(url, body).subscribe((res:any)=>{
          this.datasAccount = res;
        });
      }
      else {
        this.productService.getAllAccount(this.count, 11).subscribe((res:any)=>{
          this.datasAccount = res
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
  closeAccount(){
    this.count = 0;
    const url = 'http://localhost:8080/account/closeAccountLowLevel';
    const body = '';
    this.productService.putData(url, body).subscribe((res:any)=>{
      this.datasCheckAccuont = res;
      for(let item of this.datasCheckAccuont){
        const url = 'http://localhost:8080/notification/addNewNotification';
        const body = {mainContentId: 4, accountId: item.cardNumber};
        this.productService.postData(url, body).subscribe();
      }
      if(this.datasCheckAccuont.length > 0){
        this.mess = 'Đã khóa '+this.datasCheckAccuont.length+' tài khoản';
      }else this.mess = 'Không có tài khoản phù hợp để khóa'
      this.isMess = true;
    });
  }
  clickManageUser(){
    this.count = 0;
    this.getAll(this.count, 11);
  }
  changWeb(){
    return{
      'opacity':this.isNewAccount ? '0.2':'1',
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
    if(this.isCheckRequest == true){
      this.productService.getSearchAccount(this.inputValue, this.count, 11).subscribe((res:any)=>{
        this.datasAccount = res
      })
    }
    else if(this.isCheckSelect == true){
      let status = '';
      const url = 'http://localhost:8080/account/findAccountByTimeCreate/'+this.count+'/11';
      if(this.status != 'Tất cả trạng thái') status = this.status;
      const body = {start: this.start, end: this.end, status: status}
      this.productService.postData(url, body).subscribe((res:any)=>{
        this.datasAccount = res;
      });
    }
    else {
      this.productService.getAllAccount(this.count, 11).subscribe((res:any)=>{
        this.datasAccount = res
      })
    }
  }

  getAllCountPage(){
    this.productService.getAllCountPage().subscribe((res:any) => {
      this.totalPages = Math.ceil(res/11);
    })
  }
}
