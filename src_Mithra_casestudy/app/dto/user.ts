
/**
 * Date: 09-06-2025
 * Author: Mithra M
 * Defines the structure of user data used throughout the system,
 * including profile information, role, and status.
 */

export interface UserDTO
{
    userId?: number;
    userName:string; 
    email:string;
    password:string;
    phoneNumber:string;
    roles:string;
}