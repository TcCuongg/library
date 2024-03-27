import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product.service";
import {Account, Storage} from "../app.module";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manage-storage',
  templateUrl: './manage-storage.component.html',
  styleUrls: ['./manage-storage.component.css']
})
export class ManageStorageComponent implements OnInit{
  datasStorage:Storage[]=[];
  count = 0;
  status = false;
  inputValue:string = '';
  storageName:string = '';
  storageAddress:string = '';
  mess = '';
  statusStorage = 'Tất cả trạng thái';
  listStatus = [{status:'Tất cả trạng thái'}, {status:'Mở cửa'}, {status:'Đóng cửa'}]
  valueSave:any;


  admin:Account | undefined;

  logOut = false;
  isListStatus = false;
  isMess = false;
  constructor(private productService: ProductService,
              private router: Router) {}
  ngOnInit() {
    this.getAllStorage(this.count);
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
  }

  clickMoreStatus(){
    this.isListStatus = !this.isListStatus;
  }

  changeMoreStatus(){
    return{
      'display':this.isListStatus?'block':'none',
    }
  }
  selectStatus(string:string){
    this.statusStorage = string;
  }

  addNewStorage(){
    const url = 'http://localhost:8080/storage/addNewStorage/' + this.totalPages + '/' + 3;
    const body = {id: 0, phone: this.storageName, location: this.storageAddress, status: 'Đóng cửa'};
    let dataCheck:Storage[]=[]
    this.productService.postData(url, body).subscribe((res:any)=>{
      dataCheck = res;
      if(dataCheck.length == 0) this.mess='Thêm không thành công vui lòng kiểm tra lại thông tin nhập';
      else this.mess = 'Thêm kho mới thành công';
      this.isMess = true;
      this.getAllStorage(this.count);
    });
  }

  getAllStorage(count: number){
    this.productService.getAllStorage(count, 3).subscribe((res:any)=>{
      this.datasStorage = res
    })
    this.productService.getCountAllStorage().subscribe((res:any) => {
      this.totalPages = Math.ceil(res/3);
    })
  }

  clickMore(){
    if (this.count < this.totalPages){
      this.count += 1;
      if(this.isCheckSelect == false){
        this.productService.getAllStorage(this.count, 3).subscribe((res:any)=>{
          this.datasStorage = res;
        })
      }else {
        this.productService.getStorageByStatus(this.statusStorage, this.count, 3).subscribe((res:any)=>{
          this.datasStorage = res;
        })
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      if(this.isCheckSelect == false){
        this.productService.getAllStorage(this.count, 3).subscribe((res:any)=>{
          this.datasStorage = res;
        })
      }else {
        this.productService.getStorageByStatus(this.statusStorage, this.count, 3).subscribe((res:any)=>{
          this.datasStorage = res;
        })
      }
    }
  }
  clickNewStorage(){
    this.status = !this.status;
    this.storageName = '';
    this.storageAddress = '';
  }
  changeAddStorage(){
    return{
      'display':this.status ? 'block' : 'none'
    };
  }
  sendContentStorage(storage:Storage){
    let data = { message: storage.id};
    this.router.navigate(['/detailStorage', data]);
  }
  clickSearch(){
    this.count = 0;
    this.productService.getStorageByRequest(this.inputValue, this.count, 3).subscribe((res:any)=>{
      this.datasStorage = res
    })
    this.productService.getCountAllStorageByRequest(this.inputValue).subscribe((res:any) => {
      this.totalPages = Math.ceil(res/3);
    })
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
  clickManageStorage(){
    this.count = 0;
    this.getAllStorage(this.count);
  }

  changeStorage(storage:Storage, status:string){
    const url = 'http://localhost:8080/storage/updateStorage/' + this.count.toString() + '/3';
    const body = {storageId: storage.id, status: status}
    let dataCheck:Storage[]=[];
    this.productService.putData(url, body).subscribe((res:any)=>{
      dataCheck = res;
      if(dataCheck.length == 0) this.mess='Không có gì để thay đổi';
      else this.mess = 'Thay đổi thành công';
      this.isMess = true;
      this.getAllStorage(this.count);
    });
  }
  changeMess(){
    return{
      'display':this.isMess ? 'block':'none',
    }
  }
  clickCloseMess(){
    this.isMess = false;
    this.mess = '';
  }
  isCheckSelect = false;
  selectStorage(){
    if(this.statusStorage != "Tất cả trạng thái"){
      this.count = 0;
      this.isCheckSelect = true;
      this.productService.getStorageByStatus(this.statusStorage, this.count, 3).subscribe((res:any)=>{
        this.datasStorage = res;
      })
    }
  }




  totalPages = 1;
  currentPage: number = 1;
  visiblePages: number = 3;

  getVisiblePages(): number[] {
    let pages: number[] = [];
    let startPage: number = Math.max(this.count, 2);
    let endPage: number = Math.min(startPage + this.visiblePages - 1, this.totalPages - 1);
    if(endPage)
      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }
    return pages;
  }

  selectPage(page: number) {
    this.count = page - 1;
    this.productService.getAllStorage(this.count, 3).subscribe((res:any)=>{
      this.datasStorage = res
    })
  }
}
