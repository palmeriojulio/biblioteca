import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { API } from "app.api";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  // Variável que recebe a URL base do backend
  localUrl: string;

  constructor(private http: HttpClient) {
    this.localUrl = `${API}biblioteca/`;
  }

  InfoDashboard() {
    return this.http.get(`${this.localUrl}dashboard`);
  }
}
