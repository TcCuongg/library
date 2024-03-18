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

  valueSave:any;


  admin:Account | undefined;

  logOut = false;
  constructor(private productService: ProductService,
              private router: Router) {}
  ngOnInit() {
    this.getAllStorage(this.count);
    this.valueSave = window.localStorage;
    this.admin = JSON.parse(this.valueSave.getItem('value'));
  }

  addNewStorage(){
    if(this.storageName != '' && this.storageAddress != ''){
      const url = 'http://localhost:8080/storage/addNewStorage/' + this.count + '/' + 3;
      const body = {id: 0, phone: this.storageName, location: this.storageAddress};

      this.productService.postData(url, body).subscribe((res:any)=>{
        this.datasStorage = res
      });
    }
  }

  getAllStorage(count: number){
    this.productService.getAllStorage(count, 3).subscribe((res:any)=>{
      this.datasStorage = res
    })
  }

  datasCheckStorage:Storage[] = []
  clickMore(){
    if (this.datasStorage.length==3){
      this.productService.getAllStorage(this.count + 1, 3).subscribe((res:any)=>{
        this.datasCheckStorage = res
      })
      if(this.datasCheckStorage.length != 0){
        this.count = this.count + 1;
        this.datasStorage = this.datasCheckStorage;
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      this.getAllStorage(this.count);
    }
  }
  clickNewStorage(){
    this.status = !this.status;
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
    if(status != ''){
      const url = 'http://localhost:8080/storage/updateStorage/' + this.count.toString() + '/' + 3;
      const body = {storageId: storage.id, status: status}

      this.productService.putData(url, body).subscribe((res:any)=>{
        this.datasStorage = res
      });
    }
  }
}
