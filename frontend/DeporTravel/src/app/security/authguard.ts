import {ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import {inject, Injectable} from "@angular/core";
import { StorageService } from "../services/storge.service";

export const canActivate: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const storageService = inject(StorageService);
  const router = inject(Router);
  if (!storageService.isLoggedIn()) {
    router.navigateByUrl('/login').then();
    return false;
  } else {
    return true;
  }
}
