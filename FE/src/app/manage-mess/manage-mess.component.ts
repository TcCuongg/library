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
  getAllMess(count:number, step:number){
    if(this.isCheck == false){
      this.productService.getAllMess(count, step).subscribe((res:any)=>{
        this.datasMess = res;
      })
    }
    else {
      const url = 'http://localhost:8080/account/findMessByTimeSent/'+count+'/'+step;
      const body = {start: this.start, end: this.end, status: ''};
      this.productService.postData(url, body).subscribe((res:any) => {
        this.datasMess = res;
      })
    }
  }
  clickMore(){
    if (this.datasMess.length==11){
      if(this.isCheck == false){
        this.productService.getAllMess(this.count + 1, 11).subscribe((res:any)=>{
          this.datasCheckMess = res;
        })
      }
      else {
        const url = 'http://localhost:8080/account/findMessByTimeSent/'+this.count+1+'/'+11;
        const body = {start: this.start, end: this.end, status: ''};
        this.productService.postData(url, body).subscribe((res:any) => {
          this.datasCheckMess = res;
        })
      }
      if(this.datasCheckMess.length != 0){
        this.count += 1;
        this.datasMess = this.datasCheckMess;
      }
    }
  }

  clickLast(){
    if(this.count - 1 >= 0 ){
      this.count = this.count - 1;
      this.getAllMess(this.count, 11);
    }
  }
  clickSearch(){
    this.count = 0;
    this.productService.getAllMessByRequest(this.inputValue, this.count, 11).subscribe((res:any)=>{
      this.datasMess = res
    })
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
    if(this.valueTitle != '' && this.valueContent != ''){
      let range = this.count.toString() + '/11';
      const url = 'http://localhost:8080/account/addNewMess/' + range;
      const body = {title: this.valueTitle, content: this.valueContent}
      this.productService.postData(url, body).subscribe((res:any)=>{
        this.datasMess = res
      });
    }
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
    this.count = 0;
    this.isCheck = true;
    const url = 'http://localhost:8080/account/findMessByTimeSent/'+this.count+'/11';
    const body = {start: this.start, end: this.end, status: ''};
    this.productService.postData(url, body).subscribe((res:any) => {
      this.datasMess = res;
    })
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
}
