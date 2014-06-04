package Levels;

import java.util.ArrayList;

import Templates.GameInfo;
import Templates.GameStateManager;

public class Floor {

	Room[][] rooms;
	public static int numOfRooms = 0, minRooms;
	public int rows, columns;
	static int randomNumber;

	GameStateManager gm;
	GameInfo in;
	
	public Floor(GameStateManager gm, GameInfo in) {
		this.gm = gm;
		this.in = in;
		
		int min = 8, max = 10;
		rows = (int) (Math.random() * (max - min + 1) + min);
		columns = (int) (Math.random() * (max - min + 1) + min);
		rooms = new Room[rows][columns];

		minRooms = (int) ((double) (rows / 2) + (double) (columns / 2));

		randomNumber = (int) (Math.random() * (7) - 3);


		System.out.println(randomNumber);
	}

	public void calculateMap3() {

		int x = 0;
		int y = 0;

		int max = Math.max(rows, columns);
		int min = Math.min(rows, columns);
		rooms[x][y] = new Room(gm,in);

		int rLim = 2, uLim = 2, dLim = 2, lLim = 2;
		int rx, ry;
		int calcs = 0;

		while (numOfRooms < minRooms + randomNumber) {
			rx = x;
			ry = y;

			calcs++;
			rLim = 2;
			uLim = 2;
			dLim = 2;
			lLim = 2;

			if (x < 2)
				lLim = x;
			if (x > rows - 2)
				rLim = 1;
			if (y < 2)
				uLim = y;
			if (y > columns - 2)
				dLim = 1;

			rx = (int) (Math.random() * (rLim + lLim + 1));

			ry = (int) (Math.random() * (dLim + uLim + 1));

			if (rooms[rx][ry] == null) {
				numOfRooms++;
				x = rx;
				y = ry;

				rooms[x][y] = new Room(gm,in);
			}
		}
		System.out.println("Method 3 complete with " + calcs + " calculations");
	}

	public void calculateMap2() {

		int max = Math.max(rows, columns);
		int min = Math.min(rows, columns);
		int calcs = 0;
		int ry, rx;

		rooms[rows / 2][columns / 2] = new Room(gm,in);

		while (numOfRooms < minRooms + randomNumber) {
			ry = (int) (Math.random() * (columns - 1));
			rx = (int) (Math.random() * (rows - 1));

			calcs++;

			if (hasNeighboringRoom(rx, ry))
				if (rooms[rx][ry] == null) {
					rooms[rx][ry] = new Room(gm,in);
					numOfRooms++;
				}
		}
		System.out.println("Method 2 complete with " + calcs + " calculations");
	}

	public boolean hasNeighboringRoom(int x, int y) {
		if (x > 0)
			if (rooms[x - 1][y] != null)
				return true;
		if (y > 0)
			if (rooms[x][y - 1] != null)
				return true;
		if (y < columns - 1)
			if (rooms[x][y + 1] != null)
				return true;
		if (x < rows - 1)
			if (rooms[x + 1][y] != null)
				return true;
		return false;
	}

	public void calculateMap() {

		int calcs = 0;
		int r;
		int xx = rows;
		int yy = columns;
		int x, y, temp;
		x = xx / 2;
		y = yy / 2;

		while (numOfRooms < minRooms + randomNumber+1) {
			r = (int) (Math.random() * (4) + 1);
			// 1 is x--;
			// 2 is y--;
			// 3 is x++;
			// 4 is y++;

			temp = -1;

			if (r % 2 == 0) {
				// change y

				if (r == 2) {
					temp = y - 1;
					if (temp >= 0)
						y = temp;
				} else if (r == 4) {
					temp = y + 1;
					if (temp < columns)
						y = temp;
				}

			} else if (r % 2 == 1) {
				// change x

				if (r == 1) {
					temp = x - 1;
					if (temp >= 0)
						x = temp;
				} else if (r == 3) {
					temp = x + 1;
					if (temp < rows)
						x = temp;
				}
			}
			if (x == temp || y == temp)
				if (rooms[x][y] == null) {
					rooms[x][y] = new Room(gm,in);
					numOfRooms++;
				}
			calcs++;
		}
		System.out.println("Method 1 complete with " + calcs + " calculations");
	}

	public void connectRooms() {
		for (int x = 0; x < rows; x++)
			for (int y = 0; y < columns; y++) {
				Room right = null, left = null, up = null, down = null;
				if (x > 0)
					left = rooms[x - 1][y];
				if (y > 0)
					up = rooms[x][y - 1];
				if (x < rows - 1)
					right = rooms[x + 1][y];
				if (y < columns - 1)
					down = rooms[x][y + 1];

				rooms[x][y].setsides(left, up, right, down);
			}
	}

	public Room getRoom(int x, int y) {
		return rooms[x][y];
	}

	public void clearFloorMap() {
		rooms = new Room[rows][columns];
		numOfRooms = 0;
	}

	public void PrintFloor() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (rooms[i][j] == null)
					System.out.print("0 ");
				else
					System.out.print("1 ");
			}
			System.out.println();
		}
	}
}
