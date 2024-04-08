import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { DatePipe } from '@angular/common';

import { AppComponent } from './app.component';
import { BuyComponent } from './buy/buy.component';
import { MainComponent } from './main/main.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { MoreComponent } from './more/more.component';
import { RouterModule , Routes} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ManageStyleComponent } from './manage-style/manage-style.component';
import {HttpClientModule} from "@angular/common/http";
import { ManageStorageComponent } from './manage-storage/manage-storage.component';
import { ManageMessComponent } from './manage-mess/manage-mess.component';
import { DetailStorageComponent } from './detail-storage/detail-storage.component';
import { ManageBookComponent } from './manage-book/manage-book.component';
import { ManageAuthorComponent } from './manage-author/manage-author.component';
import { ManagePayComponent } from './manage-pay/manage-pay.component';
import { ManageTurnoverComponent } from './manage-turnover/manage-turnover.component';
import { OrderTermComponent } from './order-term/order-term.component';

const appRoutes : Routes =[
  {path: 'bookStore', component: MainComponent},
  {path: 'Buy', component: BuyComponent},
  {path: 'moreBook', component: MoreComponent},

  {path: 'manageUser', component: ManageUserComponent},
  {path: 'manageStyle', component: ManageStyleComponent},
  {path: 'manageStorage', component: ManageStorageComponent},
  {path: 'manageMess', component: ManageMessComponent},
  {path: 'manageBook', component: ManageBookComponent},

  {path: 'detailStorage', component: DetailStorageComponent},

  {path:'manageAuthor', component: ManageAuthorComponent},
  {path:'managePay', component: ManagePayComponent},
  {path:'manageTurnover', component: ManageTurnoverComponent},
  {path:'orderTerm', component: OrderTermComponent},
]
@NgModule({
  declarations: [
    AppComponent,
    BuyComponent,
    MainComponent,
    ManageUserComponent,
    MoreComponent,
    ManageStyleComponent,
    ManageStorageComponent,
    ManageMessComponent,
    DetailStorageComponent,
    ManageBookComponent,
    ManageAuthorComponent,
    ManagePayComponent,
    ManageTurnoverComponent,
    OrderTermComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: true}
    ),
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

export class Book{
  id:number | undefined;
  title:string | undefined;
  category:string | undefined;
  author:string | undefined;
  image:any | undefined;
  content:string | undefined;
  cost:number | undefined;
  bookId:number | undefined;
  authorId:number | undefined;
  categoryId:number | undefined;
  follow:number | undefined;
  quantity:number | undefined;
  sale:number | undefined;
  importTime:Date | undefined;
  status:string | undefined;
  account:string | undefined;
}

export class Buy{
  bookStorageId:number|undefined;
  accountId:number|undefined;
  status:string|undefined;
  cost:number|undefined;
  quantity:number|undefined;
}
export class Cart{
  bookStorageId:number|undefined;
  accountId:number|undefined;
}

export class Account{
  cardNumber:number | undefined;
  name:string | undefined;
  email:string | undefined;
  status:string | undefined;
  avatar:any | undefined;
  phone: number | undefined;
  address:string | undefined;
  level:number | undefined;
  type:string | undefined;
  password:string | undefined;
}

export class Storage{
  id:number | undefined;
  phone:number | undefined;
  location:string | undefined;
  status:string | undefined;
}

export class Category{
  id:number | undefined;
  name:string | undefined;
  sale:number | undefined;
}

export class Mess{
  type:string | undefined;
  content:string | undefined;
  email:string | undefined;
  sent:Date | undefined;
}

export class Author{
  id:number | undefined;
  name:string | undefined;
  date:Date | undefined;
  address:string | undefined;
  phone:number | undefined;
}

export class BookPay{
  buyId:number|undefined;
  bookStorageId:number|undefined;
  accountName:string|undefined;
  accountPhone:string|undefined;
  accountEmail:string|undefined;
  accountAddress:string|undefined;
  bookTitle:string|undefined;
  buyCost:number|undefined;
  buyQuantity:number|undefined;
  bookStrongQuantity:number|undefined;
  employee:string|undefined;
  buyStatus:string|undefined;
}
