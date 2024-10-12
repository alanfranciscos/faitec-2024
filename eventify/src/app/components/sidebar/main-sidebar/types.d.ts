export interface SideBarItensType {
  title: string;
  isSelected: boolean;
  routerLink?: string;
  icon: string;
  children?: Array<SideBarItensType>;
}
