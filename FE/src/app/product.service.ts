import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map, Observable, of} from 'rxjs';
import {Account, Author, Book, BookPay, Category, Mess, Storage} from '../app/app.module';

const httpOptions ={
  headers:new HttpHeaders({'Content-Type': 'application/octet-stream'})
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient:HttpClient) { }
//Book
  getAllBook(count:number, step:number):Observable<Book[]>{
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getAllBook/' + count.toString() + '/' + step.toString()).pipe(
    )
  }
  getTopBook(){
    return this.httpClient.get<Book>('http://localhost:8080/book/getTopBook').pipe();
  }
  getBookFollowDesc(count:number, step:number):Observable<Book[]>{
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getBookFollowDesc/' + count.toString() + '/' + step.toString()).pipe(
    )
  }
  getBookByTitle(title:string, count:number, step:number):Observable<Book[]>{
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getBookByTitle/' + count.toString() + '/' + step.toString() + '/' + title).pipe(
    )
  }
  getBookByBookId(id:number):Observable<Book>{
    return this.httpClient.get<Book>('http://localhost:8080/book/getBookByBookStorageId/' + id.toString()).pipe()
  }
  getAllBookByAccountCart(accountId:number, count:number, size:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getAllBookByAccountCart/' + accountId.toString() + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getAllBookByAccountBuy(accountId:number, count:number, size:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getAllBookByAccountBuy/' + accountId.toString() + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getAllBookByAccountBuyNew(accountId:number, count:number, size:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getAllBookByAccountBuyNew/' + accountId.toString() + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getAllBookByAccountBuyDelivered(accountId:number, count:number, size:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getAllBookByAccountBuyDelivered/' + accountId.toString() + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getAllBookByAccountBuyDelivering(accountId:number, count:number, size:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getAllBookByAccountBuyDelivering/' + accountId.toString() + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getBookByCategory(title:string, count:number, step:number):Observable<Book[]>{
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getBookByCategory/' + title + '/' + count.toString() + '/' + step.toString()).pipe(
    )
  }
  getBookByStorage(storageId:number, count:number, size:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getBookByStorage/' + storageId.toString() + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getBookByStorageIdAndCategory(storageId:number, category:string, count:number, size:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getBookByStorageIdAndCategory/' + storageId.toString() + '/' + category + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getBookRemainsZero(storageId:number, count:number, size:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getBookRemainsZero/' + storageId.toString() + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getBookByStorageAndRequest(storageId:number, request:string, count:number, size:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/storage/getBookByStorageAndRequest/' + storageId.toString() + '/' + request + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getAllMess(count:number, size:number){
    return this.httpClient.get<Mess[]>('http://localhost:8080/account/findAllMess/' + count.toString() + '/' + size.toString()).pipe()
  }
  getAllMessByRequest(request:string, count:number, size:number){
    return this.httpClient.get<Mess[]>('http://localhost:8080/account/findAllMessByRequest/' + request + '/' + count.toString() + '/' + size.toString()).pipe()
  }
  getBookByRequest(request:string, count:number, step:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getBookByRequest/' + request + '/' + count.toString() + '/' + step.toString()).pipe(
    )
  }
  getBookByBookStorageOnStorage(bookStorageId:number, count:number, step:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getBookByBookStorageOnStorage/'+bookStorageId.toString()+'/'+count.toString()+'/'+step.toString()).pipe()
  }




  //BookPay
  getAllBookPay(count:number, step:number){
    return this.httpClient.get<BookPay[]>('http://localhost:8080/book/getAllBookPay/' + count + '/' + step).pipe()
  }
  getBookPayByDelivering(count:number, step:number){
    return this.httpClient.get<BookPay[]>('http://localhost:8080/book/getBookPayByDelivering/' + count + '/' + step).pipe()
  }
  getBookPayByBom(count:number, step:number){
    return this.httpClient.get<BookPay[]>('http://localhost:8080/book/getBookPayByBom/' + count + '/' + step).pipe()
  }
  getBookPayByRequest(request:string, option:number, count:number, size:number){
    return this.httpClient.get<BookPay[]>('http://localhost:8080/book/getBookPayByRequest/' + request + '/' + option.toString() + '/' + count.toString() + '/' + size.toString()).pipe()
  }
  getBookPayByDelivered(count:number, size:number){
    return this.httpClient.get<BookPay[]>('http://localhost:8080/book/getBookPayByDelivered/' + count + '/' + size).pipe()
  }
  getBookPayByDeliveredRequest(request:string, count:number, size:number){
    return this.httpClient.get<BookPay[]>('http://localhost:8080/book/getBookPayByDeliveredRequest/' + request + '/' + count + '/' + size).pipe()
  }



  //Author
  getAllAuthor(count:number, size:number){
    return this.httpClient.get<Author[]>('http://localhost:8080/author/getAllAuthor/' + count + '/' + size).pipe()
  }
  getAuthorByNameOrPhone(request:string, count:number, size:number){
    return this.httpClient.get<Author[]>('http://localhost:8080/author/getAuthorByNameOrPhone/' + request + '/' + count + '/' + size).pipe()
  }



//Account
  getSearchAccount(content:string, count:number, step:number){
    return this.httpClient.get<Account[]>('http://localhost:8080/account/findAllByRequest/' + content + '/' + count.toString() + '/' + step.toString()).pipe(
    )
  }
  getAllAccount(count:number, step:number):Observable<Account[]>{
    return this.httpClient.get<Account[]>('http://localhost:8080/account/getAllAccount/' + count.toString() + '/' + step.toString()).pipe(
    )
  }
  getAllAccountStatus(){
    return this.httpClient.get<string[]>('http://localhost:8080/account/findAllAccountStatus').pipe()
  }


  //Category
  getAllCategory(){
    return this.httpClient.get<Category[]>('http://localhost:8080/category/getAllCategory').pipe(
    )
  }
  getCategory(count:number, size:number){
    return this.httpClient.get<Category[]>('http://localhost:8080/category/getCategory/' + count.toString() + '/' + size.toString()).pipe()
  }
  getCategoryByName(title:string, count:number, size:number){
    return this.httpClient.get<Category[]>('http://localhost:8080/category/getCategoryByTitle/' + title + '/' + count.toString() + '/' + size.toString()).pipe()
  }

  //Storage
  getAllStorage(count:number, size:number){
    return this.httpClient.get<Storage[]>('http://localhost:8080/storage/getAllStorage/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getStorageByRequest(request:string, count:number, size:number){
    return this.httpClient.get<Storage[]>('http://localhost:8080/storage/getStorageByRequest/' + request + '/' + count.toString() + '/' + size.toString()).pipe(
    )
  }
  getStorageByStatus(status:string, count:number, size:number){
    return this.httpClient.get<Storage[]>('http://localhost:8080/storage/findStorageByStatus/'+status+'/'+count.toString()+'/'+size.toString()).pipe()
  }


  //BookManage
  getAllBookManage(count:number, step:number):Observable<Book[]>{
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getAllBookManage/' + count.toString() + '/' + step.toString()).pipe(
    )
  }
  getBookManageByRequest(request:string, count:number, step:number){
    return this.httpClient.get<Book[]>('http://localhost:8080/book/getBookManageByRequest/' + request + '/' + count.toString() + '/' + step.toString()).pipe(
    )
  }
  getStorageId(bookStorageId:number){
    return this.httpClient.get<number>('http://localhost:8080/bookStorage/getStorageId/'+bookStorageId.toString()).pipe()
  }


  //get List<String>
  getAllStatus(){
    return this.httpClient.get<string[]>('http://localhost:8080/book/getAllStatus').pipe(
    )
  }
  getAllCategoryName(){
    return this.httpClient.get<string[]>('http://localhost:8080/category/getAllCategoryName').pipe(
    )
  }
  getAllAuthorName(){
    return this.httpClient.get<string[]>('http://localhost:8080/author/getAllAuthorName').pipe(
    )
  }

  //get count page
  getAllCountPage(){
    return this.httpClient.get<number>('http://localhost:8080/account/getCountAllAccount').pipe()
  }
  getCountAllBookPageByRequest(request:string){
    return this.httpClient.get<number>('http://localhost:8080/book/findCountAllBookManageRequest/' + request).pipe()
  }
  getCountAllBookManage(){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountAllBookManage').pipe()
  }
  getCountAllStorage(){
    return this.httpClient.get<number>('http://localhost:8080/storage/getCountAllStorage').pipe()
  }
  getCountAllStorageByRequest(request:string){
    return this.httpClient.get<number>('http://localhost:8080/storage/getCountAllStorageByRequest/' + request).pipe()
  }
  getCountBookByStorage(id:number){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookByStorage/'+id.toString()).pipe()
  }
  getCountAllBookByStorageAndRequest(id:number, request:string){
    return this.httpClient.get<number>('http://localhost:8080/storage/getCountAllBookByStorageAndRequest/'+id.toString()+'/'+request).pipe()
  }
  getCountBookRemainsZero(id:number){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookRemainsZero/'+id.toString()).pipe()
  }
  getCountCategories(){
    return this.httpClient.get<number>('http://localhost:8080/category/getCountCategories').pipe()
  }
  getCountAllMess(){
    return this.httpClient.get<number>('http://localhost:8080/account/getCountAllMess').pipe()
  }
  getCountAllMessByRequest(request:string){
    return this.httpClient.get<number>('http://localhost:8080/account/getCountAllMessByRequest/' + request).pipe()
  }
  getCountAllAuthor(){
    return this.httpClient.get<number>('http://localhost:8080/author/getCountAllAuthor').pipe()
  }
  getCountAuthorByNameOrPhone(request:string){
    return this.httpClient.get<number>('http://localhost:8080/author/getCountAuthorByNameOrPhone/' + request).pipe()
  }
  getCountAllBookPay(){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountAllBookPay').pipe()
  }
  getCountBookPayByDelivering(){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookPayByDelivering').pipe()
  }
  getCountBookPayByBom(){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookPayByBom').pipe()
  }
  getCountBookPayByRequest(request:string, option:number){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookPayByRequest/' + request + '/' + option.toString()).pipe()
  }
  getCountBookPayByDelivered(){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookPayByDelivered').pipe()
  }
  getCountBookPayByDeliveredRequest(request:string){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookPayByDeliveredRequest/' + request).pipe()
  }
  getCountBookByStorageIdAndCategory(storageId:number, category:string){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookByStorageIdAndCategory/'+storageId.toString()+'/'+category).pipe()
  }
  getCountBookFollowDesc(){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookFollowDesc').pipe()
  }
  getCountAllBook(){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountAllBook').pipe()
  }
  getCountAllBookByAccountCart(accountId:number){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountAllBookByAccountCart/'+accountId.toString()).pipe()
  }
  getCountAllBookByAccountBuyNew(accountId:number){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountAllBookByAccountBuyNew/'+accountId.toString()).pipe()
  }
  getCountAllBookByAccountBuyDelivering(accountId:number){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountAllBookByAccountBuyDelivering/'+accountId.toString()).pipe()
  }
  getCountAllBookByAccountBuyDelivered(accountId:number){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountAllBookByAccountBuyDelivered/'+accountId.toString()).pipe()
  }
  getCountBookByCategory(category:string){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookByCategory/'+category).pipe()
  }
  getCountBookByTitle(bookTitle:string){
    return this.httpClient.get<number>('http://localhost:8080/book/getCountBookByTitle/'+bookTitle).pipe()
  }



  postData(url: string, body: any) {
    return this.httpClient.post(url, body);
  }
  putData(url:string, body:any){
    return this.httpClient.put<any>(url, body);
  }
}
