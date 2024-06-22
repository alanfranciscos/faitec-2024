export interface UserProfile {
    // id: number; // Identificador único do usuário
    name: string; // Nome do usuário
    password: string; //Senha do usuário
    nickname?: string; // Apelido do usuário
    email: string; // Email do usuário
    city?: string; // Cidade do usuário
    state?: string; // Estado do usuário (UF)
    // Outros campos conforme necessário, como data de nascimento, foto de perfil, etc.
}
