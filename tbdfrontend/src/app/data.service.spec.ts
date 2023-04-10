import {inject, TestBed} from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';


import { DataService } from './data.service';
import { QrRequest } from './dto/QrRequest';
import { StatementRequest } from './dto/StatementRequest';
import { InteracValidateRequest } from './dto/InteracValidateRequest';
import { UserDetailsRequest } from './dto/UserDetailsRequest';
import { LogOutRequest } from './dto/LogOutRequest';
import { RefreshRequest } from './dto/RefreshRequest';
import { UpiPaymentRequest } from './dto/UpiPaymentRequest';
import { of } from 'rxjs';
import { DatePipe } from '@angular/common';

describe('DataService', () => {
  let service: DataService;
  let httpMock: HttpTestingController;
  let httpClientSpy: { post: jasmine.Spy };

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
  it('should generate QR code', () => {
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




});
