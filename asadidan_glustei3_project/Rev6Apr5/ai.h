#include "api.h"

int *find_target(GameBoard *gb);

int coord_equal(int *c1, int *c2);

int move_available(GameBoard *gb, int *next_square);

int *next_position(int *coord, int *move);

int target_next(int *head, int *target);

GameBoard *update_gameboard(GameBoard *gb, int *move, int *target);

int n_predictor(int n, GameBoard *gb, int *move, int *target);

int *survival(GameBoard *gb, int n);

