import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  private dataSource = new BehaviorSubject<any>(null);
  currentData$ = this.dataSource.asObservable();

  updateData(data: any) {
    this.dataSource.next(data);
  }
}
