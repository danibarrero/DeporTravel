import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

type Moneda = 'EUR' | 'USD' | 'GBP';

@Component({
  selector: 'app-paypal',
  templateUrl: './paypal.component.html',
  styleUrls: ['./paypal.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
})
export class PaypalComponent implements OnInit {
  paypalForm!: FormGroup;
  showFormPaypal = true;
  procesando = false;
  cancelado = false;
  currencyIcon = 'fas fa-euro-sign';

  tasasCambio: Record<Moneda, number> = {
    EUR: 1,
    USD: 1.08,
    GBP: 0.85,
  };

  precioBaseEUR = 0;
  idActividad!: string;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Obtener idActividad de la ruta
    this.idActividad = this.route.snapshot.paramMap.get('idActividad') || '';

    // Obtener el amount de query param
    const amountQuery = this.route.snapshot.queryParamMap.get('amount');
    this.precioBaseEUR = amountQuery ? Number(amountQuery) : 0;

    this.paypalForm = this.fb.group({
      method: ['PayPal'],
      amount: [this.precioBaseEUR],
      currency: ['EUR'],
    });

    this.paypalForm
      .get('currency')
      ?.valueChanges.subscribe((currency: Moneda) => {
        this.convertirMoneda(currency);
        this.setCurrencyIcon(currency);
      });
  }

  convertirMoneda(currency: Moneda): void {
    const tasa = this.tasasCambio[currency];
    const nuevoMonto = +(this.precioBaseEUR * tasa).toFixed(2);
    this.paypalForm.get('amount')?.setValue(nuevoMonto, { emitEvent: false });
  }

  setCurrencyIcon(currency: Moneda): void {
    const iconos: Record<Moneda, string> = {
      EUR: 'fas fa-euro-sign',
      USD: 'fas fa-dollar-sign',
      GBP: 'fas fa-pound-sign',
    };
    this.currencyIcon = iconos[currency];
  }

  onSubmit(): void {
    if (this.paypalForm.valid) {
      this.showFormPaypal = false;
      this.procesando = true;

      setTimeout(() => {
        this.procesando = false;
        const success = Math.random() > 0.3;

        if (success) {
          this.router.navigate(['/payment/success'], {
            queryParams: { actividadId: this.idActividad },
          });
        } else {
          this.router.navigate(['/payment/cancel']);
        }
      }, 2000);
    }
  }

  onClose(): void {
    this.router.navigate(['/inicio']);
  }

  goBack(): void {
    window.history.back();
  }
}
