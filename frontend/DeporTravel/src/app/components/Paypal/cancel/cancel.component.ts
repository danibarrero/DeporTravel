import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
const channel = new BroadcastChannel('succes-paypal-channel');

@Component({
  selector: 'app-cancel',
  imports: [RouterLink],
  templateUrl: './cancel.component.html',
  styleUrl: './cancel.component.css',
})
export class CancelComponent {
  paymentId: string | null = null;
  PayerID: string | null = null;

  ngOnInit() {
    channel.postMessage({
      message: 'cancelPaypal',
      paymentId: null,
      PayerID: null,
    });
    this.onClose();
  }

  onClose() {
    window.close();
  }
}
