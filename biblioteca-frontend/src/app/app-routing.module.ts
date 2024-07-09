import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DefaultComponent } from './layout/default/default.component';
import { LoginComponent } from './modules/login/login.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { LivroComponent } from './modules/livro/livro.component';
import { LeitorComponent } from './modules/leitor/leitor.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'principal', component: DefaultComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'livro', component: LivroComponent },
      { path: 'leitor', component: LeitorComponent }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '/login' } // Redireciona para login em caso de rota não encontrada
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
