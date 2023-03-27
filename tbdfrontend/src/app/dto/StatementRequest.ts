import {DatePipe} from "@angular/common";

export interface StatementRequest{
  sessionId : string;
  fromDate : DatePipe;
  toDate : DatePipe;
}
