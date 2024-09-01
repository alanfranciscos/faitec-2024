import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { render, screen } from '@testing-library/angular';
import userEvent from '@testing-library/user-event';
import { FormsModule } from '@angular/forms';

const getSubmitButton = () => screen.getByTestId('submit') as HTMLButtonElement;
const getInput = () => screen.getByTestId('input') as HTMLInputElement;

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('O botao deve ser habilitado somente quando digitar algum texto', async () => {
    //No momento ainda não existe o service para autenticacao, portanto esta parte permanecerá comentada
    //await render (LoginComponent, {providers: [servico de autenticacao], imports: [FormsModule]});
    expect(getSubmitButton().disabled).toBe(true);

    userEvent.type(getInput(), 'TESTANDO');
    expect(getSubmitButton().disabled).toBe(false);
  })
});
