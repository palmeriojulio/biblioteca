import { NgModule } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import ptBr from '@angular/common/locales/pt';
import { NgxMaskModule } from 'ngx-mask';

import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialogModule } from '@angular/material/dialog';

import { DefaultComponent } from './default.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { LoginComponent } from 'src/app/modules/login/login.component';
import { DashboardComponent } from 'src/app/modules/dashboard/dashboard.component';
import { LivroComponent } from 'src/app/modules/livro/livro.component';
import { LivroFormComponent } from 'src/app/modules/livro-form/livro-form.component';
import { LivroInfoComponent } from 'src/app/modules/livro-info/livro-info.component';
import { LeitorComponent } from 'src/app/modules/leitor/leitor.component';
import { LeitorInfoComponent } from 'src/app/modules/leitor-info/leitor-info.component';
import { LeitorFormComponent } from 'src/app/modules/leitor-form/leitor-form.component';
import { EmprestimoFormComponent } from 'src/app/modules/emprestimo-form/emprestimo-form.component';
import { EmprestimoComponent } from 'src/app/modules/emprestimo/emprestimo.component';
import { EmprestimoInfoComponent } from 'src/app/modules/emprestimo-info/emprestimo-info.component';
import { ConfirmDialogComponent } from 'src/app/modules/confirm-dialog/confirm-dialog.component';
import { CpfPipe } from 'src/app/pipe/cpf.pipe';
import { TelefonePipe } from 'src/app/pipe/telefone.pipe';

registerLocaleData(ptBr);

@NgModule({
  declarations: [
    DefaultComponent,
    DashboardComponent,
    LoginComponent,
    LivroComponent,
    LivroFormComponent,
    LivroInfoComponent,
    LeitorComponent,
    LeitorFormComponent,
    LeitorInfoComponent,
    EmprestimoComponent,
    EmprestimoFormComponent,
    EmprestimoInfoComponent,
    ConfirmDialogComponent,
    CpfPipe,
    TelefonePipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule,
    ReactiveFormsModule,
    MatSidenavModule,
    MatDividerModule,
    MatCardModule,
    MatSelectModule,
    MatOptionModule,
    HttpClientModule,
    MatProgressBarModule,
    MatTableModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatGridListModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatChipsModule,
    MatCheckboxModule,
    MatListModule,
    MatDialogModule,
    MatTooltipModule,
    NgxMaskModule.forRoot(),
  ]
})
export class DefaultModule { }
