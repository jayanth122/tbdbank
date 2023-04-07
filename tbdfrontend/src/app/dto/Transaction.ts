import {DatePipe, Time} from "@angular/common";

enum TransactionType{
  Credit,
  Debit
}
export interface Transaction {
  transactionId : string,
  customerId : string,
  amount : number,
  roundedAmount : number,
  balance : number,
  roundedBalance : number,
  details : string,
  transactionDate : DatePipe,
  transactionTime : Time,
  transactionType : TransactionType
}
