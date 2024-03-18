import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { DatePipe } from '@angular/common';

import { AppComponent } from './app.component';
import { BuyComponent } from './buy/buy.component';
import { MainComponent } from './main/main.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { MoreComponent } from './more/more.component';
import { SearchComponent } from './search/search.component';
import { RouterModule , Routes} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ManageStyleComponent } from './manage-style/manage-style.component';
import {HttpClientModule} from "@angular/common/http";
import { ManageStorageComponent } from './manage-storage/manage-storage.component';
import { ManageMessComponent } from './manage-mess/manage-mess.component';
import { DetailStorageComponent } from './detail-storage/detail-storage.component';
import { AddBookComponent } from './add-book/add-book.component';
import { ManageBookComponent } from './manage-book/manage-book.component';
import { ManageAuthorComponent } from './manage-author/manage-author.component';
import { ManagePayComponent } from './manage-pay/manage-pay.component';

const appRoutes : Routes =[
  {path: 'bookStore', component: MainComponent},
  {path: 'Buy', component: BuyComponent},
  {path: 'moreBook', component: MoreComponent},
  {path: 'Search', component: SearchComponent},

  {path: 'manageUser', component: ManageUserComponent},
  {path: 'manageStyle', component: ManageStyleComponent},
  {path: 'manageStorage', component: ManageStorageComponent},
  {path: 'manageMess', component: ManageMessComponent},
  {path: 'manageBook', component: ManageBookComponent},

  {path: 'detailStorage', component: DetailStorageComponent},
  {path: 'addBook', component: AddBookComponent},

  {path:'manageAuthor', component: ManageAuthorComponent},
  {path:'managePay', component: ManagePayComponent}
]
@NgModule({
  declarations: [
    AppComponent,
    BuyComponent,
    MainComponent,
    ManageUserComponent,
    MoreComponent,
    SearchComponent,
    ManageStyleComponent,
    ManageStorageComponent,
    ManageMessComponent,
    DetailStorageComponent,
    AddBookComponent,
    ManageBookComponent,
    ManageAuthorComponent,
    ManagePayComponent
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
  image:string | undefined;
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
}


export class Account{
  cardNumber:number | undefined;
  name:string | undefined;
  email:string | undefined;
  status:string | undefined;
  avatar:string | undefined;
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
