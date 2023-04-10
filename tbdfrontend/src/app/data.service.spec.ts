import {inject, TestBed} from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import {Router, RouterModule} from '@angular/router';
import { DataService } from './data.service';
import { QrRequest } from './dto/QrRequest';
import { StatementRequest } from './dto/StatementRequest';
import { InteracValidateRequest } from './dto/InteracValidateRequest';
import { UserDetailsRequest } from './dto/UserDetailsRequest';
import { LogOutRequest } from './dto/LogOutRequest';
import { RefreshRequest } from './dto/RefreshRequest';
import { UpiPaymentRequest } from './dto/UpiPaymentRequest';
import {of, throwError} from 'rxjs';
import { DatePipe } from '@angular/common';

describe('DataService', () => {
  let service: DataService;
  let httpMock: HttpTestingController;
  let httpClientSpy: { post: jasmine.Spy };
  let router: Router;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DataService, DatePipe]
    });
    service = TestBed.inject(DataService);
    httpMock = TestBed.inject(HttpTestingController);
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['post']);


  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('should retrieve email', () => {
    localStorage.setItem('email', 'test@test.com');
    expect(service.getEmail()).toBe('"test@test.com"');
  });

  it('should retrieve account balance', () => {
    localStorage.setItem('accountBalance', '1234');
    expect(service.getAccountBalance()).toBe(1234);
  });

  it('should send login details', () => {
    const loginData = new FormData();
    loginData.append('username', 'testuser');
    loginData.append('password', 'testpassword');
    service.sendLoginDetails(loginData).subscribe((response) => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(`${service['url']}/login`);
    expect(req.request.method).toBe('POST');
    req.flush({});
  });
  it('should send registration details', () => {
    const registrationData = new FormData();
    registrationData.append('username', 'testuser');
    registrationData.append('password', 'testpassword');
    registrationData.append('email', 'testuser@example.com');
    registrationData.append('phone', '1234567890');
    service.sendRegistrationDetails(registrationData).subscribe(response => {
      expect(response).toBeTruthy();
    });
    const req = httpMock.expectOne(`${service['url']}/register`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(registrationData);
    req.flush({});
  });
  it('should send interac details', () => {
    const interacData = new FormData();
    // Add required form data fields
    interacData.append('amount', '10');
    interacData.append('recipient', 'johndoe@example.com');
    interacData.append('security_question', 'What is your favorite color?');
    interacData.append('security_answer', 'blue');

    service.sendInteracDetails(interacData).subscribe(response => {
      expect(response).toBeTruthy();
      // Add any additional expectations for the response
    });

    const req = httpMock.expectOne(`${service['url']}/transaction/interac`);
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(interacData);
    req.flush({}); // Optional response body
  });
  it('should set session values in the storage', () => {
    const userName = 'testUser';
    const value = '12345';
    service.setSessionValues(userName, value);
    expect(service.getSessionValues(userName)).toEqual(value);
  });

  it('should return null when trying to get a non-existent session value', () => {
    const userName = 'nonExistentUser';
    expect(service.getSessionValues(userName)).toBeNull();
  });
  it('should set verification image', () => {
    const service: DataService = TestBed.inject(DataService);
    const imageData = { /* mock image data */ };
    service.setVerificationImage(imageData);
    expect(service.verificationImage).toBe(imageData);
  });
  it('should set verification PDF data', () => {
    const pdfData = 'dummy pdf data';
    service.setVerificationPdf(pdfData);
    expect(service.verificationPdf).toEqual(pdfData);
  });
  it('should return the verification PDF', () => {
    const service: DataService = TestBed.inject(DataService);
    const pdfData = 'PDF DATA';
    service.setVerificationPdf(pdfData);
    const retrievedPdfData = service.getVerificationPdf();
    expect(retrievedPdfData).toEqual(pdfData);
  });
  it('should set and get payment QR image data', () => {
    const service: DataService = TestBed.inject(DataService);
    const qrData = 'testQRData';
    service.setPaymentQrImage(qrData);
    const retrievedQrData = service.getPaymentQrImage();
    expect(retrievedQrData).toEqual(qrData);
  });
  it('should set and get payment QR pdf data', () => {
    const service: DataService = TestBed.inject(DataService);
    const mockPdfData = { /* mock pdf data */ };
    service.setPaymentQrPdf(mockPdfData);
    const retrievedPdfData = service.getPaymentQrPdf();
    expect(retrievedPdfData).toEqual(mockPdfData);
  });
  it('should set and get statement pdf', () => {
    const statementPdfData = { data: 'statement pdf data' };
    service.setStatementPdf(statementPdfData);
    const retrievedStatementPdfData = service.getStatementPdf();
    expect(retrievedStatementPdfData).toEqual(statementPdfData);
  });
  it('should call generateQr with the correct request', () => {
    const qrRequest: QrRequest = { sessionId: 'some data' };
    httpClientSpy.post.and.returnValue(of({ success: true }));

    service.generateQr(qrRequest).subscribe(() => {
      expect(httpClientSpy.post).toHaveBeenCalledWith(`${service['url']}/qr/generateQR`, qrRequest);
    });
  });

  it('should call fetchUserDetails with the correct request', () => {
    const userDetailsRequest: UserDetailsRequest = { sessionId: 'qert' };
    httpClientSpy.post.and.returnValue(of({ success: true }));

    service.fetchUserDetails(userDetailsRequest).subscribe(() => {
      expect(httpClientSpy.post).toHaveBeenCalledWith(`${service['url']}/customer`, userDetailsRequest);
    });
  });

  it('should send interac validate request to the correct endpoint with correct data', () => {
    const interacValidateRequest: InteracValidateRequest = {
      sessionId: 'some data',
      email:'test@test.com'
    };
    httpClientSpy.post.and.returnValue(of({ success: true }));

    service.validateInterac(interacValidateRequest).subscribe(() => {
      expect(httpClientSpy.post).toHaveBeenCalledWith(
        `${service['url']}/transaction/interac/validate`,
        interacValidateRequest
      );
    });
  });

  it('should return an observable of success response after sending interac validate request', () => {
    const interacValidateRequest: InteracValidateRequest = {
      sessionId: 'some data',
      email:'test@test.com'
    };
    const expectedResponse = { success: true };
    httpClientSpy.post.and.returnValue(of(expectedResponse));

    service.validateInterac(interacValidateRequest).subscribe((response) => {
      expect(response).toEqual(expectedResponse);
    });
  });

  it('should return user details on successful request', () => {
    const userDetailsRequest: UserDetailsRequest = { sessionId: 'some data' };
    const userDetailsResponse = { name: 'John Doe', email: 'johndoe@example.com' };
    httpClientSpy.post.and.returnValue(of(userDetailsResponse));
    service.fetchUserDetails(userDetailsRequest).subscribe((response) => {
      expect(response).toEqual(userDetailsResponse);
    });
  });

  it('should handle errors', () => {
    const userDetailsRequest: UserDetailsRequest = { sessionId: 'some data' };
    const errorResponse = { status: 500, message: 'Internal Server Error' };
    httpClientSpy.post.and.returnValue(throwError(errorResponse));
    service.fetchUserDetails(userDetailsRequest).subscribe(
      (response) => {},
      (error) => {
        expect(error).toEqual(errorResponse);
      }
    );
  });
  it('should update session and store account balance and email when user details are fetched successfully', () => {
    // Arrange
    const sessionId = 'session123';
    const customer = {
      roundedAccountBalance: '1000',
      email: 'test@example.com'
    };
    const response = {
      success: true,
      sessionId: 'newSessionId',
      customer: customer
    };
    spyOn(localStorage, 'getItem').and.returnValue(sessionId);
    spyOn(service, 'fetchUserDetails').and.returnValue(of(response));
    spyOn(localStorage, 'setItem');
    spyOn(service, 'updateSession');

    // Act
    service.fetchCustomerDetails();

    // Assert
    expect(service.fetchUserDetails).toHaveBeenCalledWith({ sessionId: sessionId });
    expect(service.updateSession).toHaveBeenCalledWith(true, 'newSessionId');
    expect(localStorage.setItem).toHaveBeenCalledWith('accountBalance', customer.roundedAccountBalance.toString());
    expect(localStorage.setItem).toHaveBeenCalledWith('email', JSON.stringify(customer.email));
  });



  it('should call logOut with the correct request', () => {
    // Arrange
    const logOutRequest: LogOutRequest = { sessionId: 'some session id' };
    httpClientSpy.post.and.returnValue(of({ success: true }));

    // Act
    service.logOut(logOutRequest).subscribe(() => {

      // Assert
      expect(httpClientSpy.post).toHaveBeenCalledWith(`${service['url']}/logout`, logOutRequest, {responseType: "text"});
    });
  });

  it('should make a UPI payment request', () => {
    // Arrange
    const textEncoder = new TextEncoder();
    const qrImage = "Hello, world!";
    const myUint8Array = textEncoder.encode(qrImage);

    const upiPaymentRequest: UpiPaymentRequest = {
      sessionId: 'some session id',
      qrImage: myUint8Array
    };
    httpClientSpy.post.and.returnValue(of({success: true}));

    // Act

    service.upiPayment(upiPaymentRequest).subscribe(() => {
      // Assert
      expect(httpClientSpy.post).toHaveBeenCalledWith(`${service['url']}/qr/QRPayment`, upiPaymentRequest);
    });
  });

  it('should set isLoginValid and sessionId in localStorage and start timeout', () => {
    // Arrange
    spyOn(localStorage, 'setItem');
    spyOn(window, 'setTimeout');
    const isValid = true;
    const newSessionId = '12345';

    // Act
    service.setIsLoginValid(isValid, newSessionId);

    // Assert
    expect(service.isLoginValid).toBe(isValid);
    expect(localStorage.setItem).toHaveBeenCalledWith('sessionId', newSessionId);
    expect(window.setTimeout).toHaveBeenCalled();
  });
  /*it('should update session correctly', () => {
    // Arrange
    spyOn(localStorage, 'setItem');
    const setTimeoutSpy = spyOn(window, 'setTimeout').and.callThrough();
    service.startTime = 0;
    service.timeoutId = 123;

    // Act
    service.updateSession(true, 'newSessionId');

    // Assert
    expect(clearTimeout).toHaveBeenCalledWith(service.timeoutId);
    expect(service.isLoginValid).toBe(true);
    expect(localStorage.setItem).toHaveBeenCalledWith('sessionId', 'newSessionId');
    expect(setTimeoutSpy).toHaveBeenCalled();
    const args = (setTimeoutSpy.calls.mostRecent().args as Function[])[0];
    expect(args.length).toBe(1);
    if (args && args.length > 0) {
    args[0](); // call the function passed to setTimeout to trigger clearTimeout and localStorage.setItem
    expect(localStorage.setItem).toHaveBeenCalledWith('timeoutState', JSON.stringify({
      remainingTime: 300000 - (Date.now() - service.startTime)
    }));
  });
*/

});




  /*it('should generate QR code', () => {
    const qrRequest: QrRequest = {
      amount: 100,
      currency: 'USD',
      description: 'test QR code',
      merchantId: '123456'
    };
    const expectedData = { data: 'QR code generated' };
    httpClientSpy.post.and.returnValue(of(expectedData));

    service.generateQr(qrRequest).subscribe(
      data => expect(data).toEqual(expectedData, 'expected QR code data'),
      fail
    );
    expect(httpClientSpy.post.calls.count()).toBe(1, 'one call');
  });

*/



