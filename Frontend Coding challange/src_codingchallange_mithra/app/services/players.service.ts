import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Player } from '../model/players';

@Injectable({
  providedIn: 'root'
})
export class PlayersService {

  constructor(private http: HttpClient) {}

  baseURL: string = 'http://localhost:8080/api/players/';

  // GET all players
  /*getAll(): Observable<Player[]> {
    return this.http.get<Player[]>(this.baseURL);
  }*/

  getAll(): Observable<Player[]> {
  return this.http.get<Player[]>(this.baseURL + 'getall');
}

  // POST a new player
  /*insert(body: Player): Observable<Player> {
    console.log(body);
    return this.http.post<Player>(this.baseURL, body);
  }*/

    // Correct POST endpoint matching Spring Boot controller
insert(body: Player): Observable<Player> {
  console.log(body);
  return this.http.post<Player>(this.baseURL + 'add', body);
}

 

  // PUT - update a player by ID
/*  update(playerId: number, body: Player): Observable<Player> {
    return this.http.put<Player>(this.baseURL + playerId, body);
  }
*/
update(playerId: number, body: Player): Observable<Player> {
  return this.http.put<Player>(this.baseURL + 'update/' + playerId, body);
}

  // DELETE player by ID
 /* delete(playerId: number): Observable<string> {
    return this.http.delete<string>(this.baseURL + playerId);
  }*/

  delete(playerId: number): Observable<string> {
  return this.http.delete<string>(this.baseURL + 'delete/' + playerId);
}

  // GET players by name (assuming backend supports filtering by name)
  findByName(playerName: string): Observable<Player[]> {
    console.log(playerName);
    return this.http.get<Player[]>(this.baseURL + `search/${playerName}`);
  }

 /* getPlayerById(id: number): Observable<Player> {
  return this.http.get<Player>(`${this.baseURL}${id}`);
}*/
getPlayerById(id: number): Observable<Player> {
  return this.http.get<Player>(this.baseURL + 'get/' + id);
}


}
