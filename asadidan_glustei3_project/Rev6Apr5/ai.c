#include "ai.h"

int *find_target(GameBoard *gb) {
    for (int i = 0; i < BOARD_SIZE; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (gb->cell_value[i][j] != 0) {
                int *target = calloc(2, sizeof(int));
                target[0] = j;
                target[1] = i;
                return target;
            }
        }
    }
    return NULL;
}

int coord_equal(int *c1, int *c2) {
    if (c1[0] == c2[0] && c1[1] == c2[1]) return 1;
    return 0;
}

int move_available(GameBoard *gb, int *move) {
    int *next_square = next_position(gb->snek->head->coord, move);
    if (next_square[0] > BOARD_SIZE - 1 || next_square[0] < 0 || next_square[1] > BOARD_SIZE - 1 ||
        next_square[1] < 0) { // out of range
        free(next_square);
        return 0;
    }
    if (gb->occupancy[next_square[1]][next_square[0]] &&
        !coord_equal(next_square, gb->snek->tail->coord)) { // hitting itself
        free(next_square);
        return 0;
    }
    free(next_square);
    return 1;
}

int *next_position(int *coord, int *move) {
    int *next = calloc(2, sizeof(int));
    next[0] = coord[0];
    next[1] = coord[1];
    next[move[0]] += move[1];
    return next;
}

int target_next(int *head, int *target) {
    if (target == NULL) return 0;
    if (head[0] == target[0] && abs(head[1] - target[1]) == 1) return 1;
    if (head[1] == target[1] && abs(head[0] - target[0]) == 1) return 1;
    return 0;
}

GameBoard *update_gameboard(GameBoard *gb, int *move, int *target) {
    GameBoard *new_gb = malloc(sizeof(GameBoard));
    new_gb->snek = malloc(sizeof(Snek));
    new_gb->snek->head = malloc(sizeof(SnekBlock));
    new_gb->snek->length = gb->snek->length;
    // cell value is not needed since the target has been located already
    int *new_head = next_position(gb->snek->head->coord, move);
    new_gb->snek->head->coord[0] = new_head[0];
    new_gb->snek->head->coord[1] = new_head[1];
    new_gb->snek->head->next = gb->snek->head;

    // now update occupancy and also update snake tail OR length if needed
    SnekBlock *curr = new_gb->snek->head;
    int tail_next = 0;

    while (curr->next && !tail_next) { // change
        new_gb->occupancy[curr->coord[1]][curr->coord[0]] = 1;
        if (coord_equal(curr->next->coord, gb->snek->tail->coord)) tail_next = 1;
        else curr = curr->next;
    }
    if (target_next(gb->snek->head->coord, target)) {
        new_gb->occupancy[curr->next->coord[1]][curr->next->coord[0]] = 1; // adding in previous tail
        new_gb->snek->length++;
        new_gb->snek->tail = curr->next;
    } else {
        new_gb->snek->tail = curr; // making new tail
    }

    return new_gb;
}

int n_predictor(int n, GameBoard *gb, int *move, int *target) {
    // The brain of the AI: checks if after n moves the Snake will be trapped

    if (!move_available(gb, move)) return 0; // base case 1

    if (n == 0) { // base case 2
        int sum = 0;
        int *new_move = calloc(2, sizeof(int));
        for (int ax = 0; ax < 2; ax++) {
            for (int dir = -1; dir < 2; dir += 2) {
                new_move[0] = ax;
                new_move[1] = dir;
                if (move_available(gb, new_move)) sum += 1;
            }
        }
        return sum;
    }

    // move is available, so check all possible paths after said move
    GameBoard *new_gb = update_gameboard(gb, move, target);
    int non_traps = 0;
    int *new_move = calloc(2, sizeof(int));
    for (int ax = 0; ax < 2; ax++) {
        for (int dir = -1; dir < 2; dir += 2) {
            new_move[0] = ax;
            new_move[1] = dir;
            non_traps += n_predictor(n - 1, new_gb, new_move, target); // recursive step: worst case O(4^n)
        }
    }
    return non_traps;
}


int *survival(GameBoard *gb, int n) {
    // 1. Check to see if a move towards the target that ensures n moves is possible 
    int *target = find_target(gb);
    int *head = gb->snek->head->coord;
    int target_xdir = 0;
    int target_ydir = 0;
    int *move = calloc(2, sizeof(int));

    if (target) {
        if (head[0] < target[0]) target_xdir = 1;
        else if (head[0] > target[0]) target_xdir = -1;
        if (head[1] < target[1]) target_ydir = 1;
        else if (head[1] > target[1]) target_ydir = -1;
    }

    if (target_xdir != 0) {
        move[0] = x;
        move[1] = target_xdir;
        if (n_predictor(n, gb, move, target) > 0) return move;
    }
    if (target_ydir != 0) {
        move[0] = y;
        move[1] = target_ydir;
        if (n_predictor(n, gb, move, target) > 0) return move;
    }

    // 2. Check to see if any other moves are available that ensure at least n further moves
    for (int ax = 0; ax < 2; ax++) {
        for (int dir = -1; dir < 2; dir += 2) {
            move[0] = ax;
            move[1] = dir;
            if (n_predictor(n, gb, move, target) > 0) return move;
        }
    }

    // 3. If no moves ensure at least n further moves, then choose an available move. If no moves available, go up to die.
    for (int ax = 0; ax < 2; ax++) {
        for (int dir = -1; dir < 2; dir += 2) {
            move[0] = ax;
            move[1] = dir;
            if (move_available(gb, move)) return move;
        }
    }
    return move;
}

    
    



