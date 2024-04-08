import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {ActivatedRoute} from "@angular/router";
import {Account, Author} from "../app.module";

@Component({
  selector: 'app-manage-author',
  templateUrl: './manage-author.component.html',
  styleUrls: ['./manage-author.component.css']
})
export class ManageAuthorComponent implements OnInit{
  valueSave:any;

  inputValue = '';
  timeStart = '';
  timeEnd = '';
  inputName = '';
  inputPhone = '';
  inputBirthDay = '';
  inputAddress = '';
  admin : Account | undefined;
  datasAuthor:Author[] = [];
  author:Author | undefined;

  isSetUser = false;
  userName = '';
  userEmail = '';
  userPhone = '';
  userAddress = '';
  passOld = '';
  passNew = '';

  count = 0;

  logOut = false;
  isCheckSearch = false;
  isCheckSelection = false;
  isBoxInput = false;
  isNewAuthor = false;
  isSettingAuthor = false;

  constructor(private productService: ProductService,
              private route: ActivatedRoute) {
  }
  ngOnInit() {
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem("value"));
    this.getAllAuthor(this.count);
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
  clickSettingUser(){
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

  changeBoxInput(){
    return{
      'display': this.isBoxInput ? 'block':'none',
    }
  }

  addNewAuthor(){
    let dataCheck:Author[]=[];
    const body = {id: 0, name: this.inputName, phone: this.inputPhone, date: this.inputBirthDay, address: this.inputAddress}
    this.productService.postData('http://localhost:8080/author/addNewAuthor', body).subscribe((res:any) => {
      dataCheck = res;
      if(dataCheck.length == 0) this.mess = 'Thêm mới không thành công vui lòng kiểm tra lại các trường';
      else this.mess = 'Thêm mới tác giả ' + this.inputName + ' thành công';
      this.isMess = true;
      this.getAllAuthor(this.count);
      this.closeBoxInput();
    })
  }

  updateAuthor(){
    let dataCheck:Author[]=[];
    const body = {id: this.author?.id, name: this.inputName, phone: this.inputPhone, date: this.inputBirthDay, address: this.inputAddress}
    this.productService.putData('http://localhost:8080/author/updateAuthor', body).subscribe((res:any) => {
      dataCheck = res;
      if(dataCheck.length == 0) this.mess = 'Sửa không thành công vui lòng kiểm tra lại các trường';
      else this.mess = 'Sửa tác giả ' + this.inputName + ' thành công';
      this.isMess = true;
      this.getAllAuthor(this.count);
      this.closeBoxInput();
    })
  }

  closeBoxInput(){
    this.isBoxInput = false;
    this.isNewAuthor = false;
    this.isSettingAuthor = false;
    this.inputName = '';
    this.inputPhone = '';
    this.inputBirthDay = '';
    this.inputAddress = '';
  }

  clickSetting(author:Author){
    this.author = author;
    this.inputName = this.author.name ?? '';
    this.inputPhone = this.author.phone?.toString() ?? '';
    this.inputBirthDay = this.author.date?.toString() ?? '';
    this.inputAddress = this.author.address ?? '';
  }

  clickSelect(){
    if(this.timeStart != '' || this.timeEnd != ''){
      this.count = 0;
      const url = 'http://localhost:8080/author/selectionAuthorWithBirthDay/' + this.count + '/11';
      const body = {start: this.timeStart, end: this.timeEnd, status: ''};
      this.productService.postData(url, body).subscribe((res:any) => {
        this.datasAuthor = res;
      });
      this.productService.postData('http://localhost:8080/author/countSelectionAuthorWithBirthDay', body).subscribe((res:any) => {
        if(res != 0){
          this.isCheckSelection = true;
          this.totalPages = Math.ceil(res/11);
        }
      });
    }
  }

  getAllAuthor(count:number){
    if(this.isCheckSearch == true){
      this.productService.getAuthorByNameOrPhone(this.inputValue, count, 11).subscribe((res:any) => {
        this.datasAuthor = res;
      })
      this.productService.getCountAuthorByNameOrPhone(this.inputValue).subscribe((res:any) => {
        if(res != 0){
          this.isCheckSearch = true;
          this.totalPages = Math.ceil(res/11);
        }
      })
    }
    else if(this.isCheckSelection == true){
      const url = 'http://localhost:8080/author/selectionAuthorWithBirthDay/' + count + '/11';
      const body = {start: this.timeStart, end: this.timeEnd, status: ''};
      this.productService.postData(url, body).subscribe((res:any) => {
        this.datasAuthor = res;
      });
    }
    else{
      this.productService.getAllAuthor(count, 11).subscribe((res:any) => {
        this.datasAuthor = res;
      });
      this.productService.getCountAllAuthor().subscribe((res:any) => {
        this.totalPages = Math.ceil(res/11);
      })
    }
  }

  clickSearch(){
    this.count = 0;
    this.productService.getAuthorByNameOrPhone(this.inputValue, this.count, 11).subscribe((res:any) => {
      this.datasAuthor = res;
    })
    this.productService.getCountAuthorByNameOrPhone(this.inputValue).subscribe((res:any) => {
      if(res != 0){
        this.isCheckSearch = true;
        this.totalPages = res;
      }
    })
  }

  clickMore(){
    if(this.count < this.totalPages - 1){
      this.count += 1;
      if(this.isCheckSearch == true){
        this.productService.getAuthorByNameOrPhone(this.inputValue, this.count, 11).subscribe((res:any) => {
          this.datasAuthor = res;
        })
        this.productService.getCountAuthorByNameOrPhone(this.inputValue).subscribe((res:any) => {
          if(res != 0){
            this.isCheckSearch = true;
            this.totalPages = res;
          }
        })
      }
      else if(this.isCheckSelection == true){
        const url = 'http://localhost:8080/author/selectionAuthorWithBirthDay/' + this.count + '/11';
        const body = {start: this.timeStart, end: this.timeEnd, status: ''};
        this.productService.postData(url, body).subscribe((res:any) => {
          this.datasAuthor = res;
        });
      }
      else{
        this.productService.getAllAuthor(this.count, 11).subscribe((res:any) => {
          this.datasAuthor = res;
        });
        this.productService.getCountAllAuthor().subscribe((res:any) => {
          this.totalPages = Math.ceil(res/11);
        })
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0){
      this.count -= 1;
      if(this.isCheckSearch == true){
        this.productService.getAuthorByNameOrPhone(this.inputValue, this.count, 11).subscribe((res:any) => {
          this.datasAuthor = res;
        })
        this.productService.getCountAuthorByNameOrPhone(this.inputValue).subscribe((res:any) => {
          if(res != 0){
            this.isCheckSearch = true;
            this.totalPages = res;
          }
        })
      }
      else if(this.isCheckSelection == true){
        const url = 'http://localhost:8080/author/selectionAuthorWithBirthDay/' + this.count + '/11';
        const body = {start: this.timeStart, end: this.timeEnd, status: ''};
        this.productService.postData(url, body).subscribe((res:any) => {
          this.datasAuthor = res;
        });
      }
      else{
        this.productService.getAllAuthor(this.count, 11).subscribe((res:any) => {
          this.datasAuthor = res;
        });
        this.productService.getCountAllAuthor().subscribe((res:any) => {
          this.totalPages = Math.ceil(res/11);
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
    if(this.isCheckSearch == true){
      this.productService.getAuthorByNameOrPhone(this.inputValue, this.count, 11).subscribe((res:any) => {
        this.datasAuthor = res;
      })
      this.productService.getCountAuthorByNameOrPhone(this.inputValue).subscribe((res:any) => {
        if(res != 0){
          this.isCheckSearch = true;
          this.totalPages = res;
        }
      })
    }
    else if(this.isCheckSelection == true){
      const url = 'http://localhost:8080/author/selectionAuthorWithBirthDay/' + this.count + '/11';
      const body = {start: this.timeStart, end: this.timeEnd, status: ''};
      this.productService.postData(url, body).subscribe((res:any) => {
        this.datasAuthor = res;
      });
    }
    else{
      this.productService.getAllAuthor(this.count, 11).subscribe((res:any) => {
        this.datasAuthor = res;
      });
      this.productService.getCountAllAuthor().subscribe((res:any) => {
        this.totalPages = Math.ceil(res/11);
      })
    }
  }
}
