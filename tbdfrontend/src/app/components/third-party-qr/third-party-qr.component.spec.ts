import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DomSanitizer } from '@angular/platform-browser';
import { DataService } from '../../data.service';
import { ThirdPartyQrComponent } from './third-party-qr.component';
import { saveAs } from 'file-saver';
import {HeaderComponent} from "../header/header.component";
import {FooterComponent} from "../footer/footer.component";

describe('ThirdPartyQrComponent', () => {
  let component: ThirdPartyQrComponent;
  let fixture: ComponentFixture<ThirdPartyQrComponent>;
  let sanitizerSpy: jasmine.SpyObj<DomSanitizer>;
  let dataServiceSpy: jasmine.SpyObj<DataService>;

  beforeEach(async () => {
    sanitizerSpy = jasmine.createSpyObj('DomSanitizer', ['bypassSecurityTrustUrl']);
    dataServiceSpy = jasmine.createSpyObj('DataService', ['getVerificationImage', 'getVerificationPdf']);

    await TestBed.configureTestingModule({
      declarations: [ ThirdPartyQrComponent, HeaderComponent, FooterComponent ],
      providers: [
        { provide: DomSanitizer, useValue: sanitizerSpy },
        { provide: DataService, useValue: dataServiceSpy }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ThirdPartyQrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should download a PDF', () => {
    const pdf = 'dGVzdC1wZGY='; // a valid base64-encoded string
    const pdfBlob = new Blob([new Uint8Array(atob(pdf).split('').map(char => char.charCodeAt(0)))]);
    const fileName = 'Verification.pdf';
    dataServiceSpy.getVerificationPdf.and.returnValue(pdf);
    const saveAsSpy = spyOn(saveAs, 'saveAs');
    component.downloadPdf();
    expect(saveAsSpy).toHaveBeenCalledWith(pdfBlob, fileName);
  });



  // it('should set the image URL on init', () => {
  //   const image = 'test-image';
  //   const base64Image = btoa(image);
  //   const byteCharacters = base64Image;
  //   const byteNumbers = new Array(byteCharacters.length);
  //   for (let i = 0; i < byteCharacters.length; i++) {
  //     byteNumbers[i] = byteCharacters.charCodeAt(i);
  //   }
  //   const byteArray = new Uint8Array(byteNumbers);
  //   const blob = new Blob([byteArray], { type: 'image/png' });
  //   const unsafeUrl = URL.createObjectURL(blob);
  //   sanitizerSpy.bypassSecurityTrustUrl.and.returnValue('safe-url');
  //   dataServiceSpy.getVerificationImage.and.returnValue(byteArray);
  //   spyOn(URL, 'createObjectURL').and.returnValue(unsafeUrl);
  //   component.ngOnInit();
  //   expect(sanitizerSpy.bypassSecurityTrustUrl).toHaveBeenCalledWith(unsafeUrl);
  //   expect(component.imageUrl).toBe('safe-url');
  // });
  // it('should set the image URL on init', () => {
  //   const image = 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mOUYKAAAAM0lEQVQIHWP8////fwYAAgEBAQIAAwn7EwAAAABJRU5ErkJggg==';
  //   const byteCharacters = atob(image);
  //   const byteNumbers = new Array(byteCharacters.length);
  //   for (let i = 0; i < byteCharacters.length; i++) {
  //     byteNumbers[i] = byteCharacters.charCodeAt(i);
  //   }
  //   const byteArray = new Uint8Array(byteNumbers);
  //   const blob = new Blob([byteArray], { type: 'image/png' });
  //   const unsafeUrl = URL.createObjectURL(blob);
  //   sanitizerSpy.bypassSecurityTrustUrl.and.returnValue('safe-url');
  //   dataServiceSpy.getVerificationImage.and.returnValue(byteArray);
  //   spyOn(URL, 'createObjectURL').and.returnValue(unsafeUrl);
  //   component.ngOnInit();
  //   expect(sanitizerSpy.bypassSecurityTrustUrl).toHaveBeenCalledWith(unsafeUrl);
  //   expect(component.imageUrl).toBe('safe-url');
  // });

});

