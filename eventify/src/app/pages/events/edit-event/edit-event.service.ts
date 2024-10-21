import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class EditEventService {
  private basicInfoData: any = {};
  private addressData: any = {};
  private paymentData: any = {};

  setBasicInfoData(data: any) {
    this.basicInfoData = data;
  }

  getBasicInfoData() {
    return this.basicInfoData;
  }

  setAddressData(data: any) {
    this.addressData = data;
  }

  getAddressData() {
    return this.addressData;
  }

  setPaymentData(data: any) {
    this.paymentData = data;
  }

  getPaymentData() {
    return this.paymentData;
  }

  getCompleteEventData() {
    return {
      ...this.basicInfoData,
      ...this.addressData,
      ...this.paymentData,
    };
  }
}
