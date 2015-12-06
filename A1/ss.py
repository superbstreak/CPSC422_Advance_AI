__author__ = 'paulo_apolinar'

# states -- pos = position (x,y), val = probability
# dict = {'pos': (x,y), 'val': probability}
s11 = {'pos': (1,1), 'val': 0}
    # (0.1)upS11, (0.1)upS21
    # (0.8+0.1)downS11, (0.8)down12, (0.1)down21
    # (0.8+0.1)leftS11, (0.8)left21, (0.1)left12
    # (0.1)rightS11, (0.1)right12
s12 = {'pos': (1,2), 'val': 0}
s13 = {'pos': (1,3), 'val': 0}
s21 = {'pos': (2,1), 'val': 0}
s22 = {'pos': (2,2), 'val': 0} # Probability should ALWAYS be 0. Blocked space
s23 = {'pos': (2,3), 'val': 0}
s31 = {'pos': (3,1), 'val': 0}
s32 = {'pos': (3,2), 'val': 0}
s33 = {'pos': (3,3), 'val': 0}
s41 = {'pos': (4,1), 'val': 0} # Only space with 1 wall
s42 = {'pos': (4,2), 'val': 0} # Terminal state
s43 = {'pos': (4,3), 'val': 0} # Terminal state

valid_non_terminal_states = [s11, s12, s13, s21, s23, s31, s32, s33, s41]
terminal_states = [s42, s43]


# list to make things easier for updating
# s31 = update_order[5], terminal states = update_order[9] and update_order[10]
update_order = [s11, s12, s13, s21, s23, s31, s32, s33, s41, s42, s43]


# Grid set up --
# col 1| col 2| col 3| col 4
# (1,3), (2,3), (3,3), (4,3) -- row 3
# (1,2), (2,2), (3,2), (4,2) -- row 2
# (1,1), (1,2), (3,1), (4,1) -- row 1
grid = [[s13,s23,s33,s43],[s12,s22,s32,s42], [s11,s21,s31,s41]]

# prints grid
def printGrid():
    for x in grid:
        print x

# returns row number r
def row(r):
    if r == 1: return grid[2]
    if r == 2: return grid[1]
    if r == 3: return grid[0]

# returns column number c
def col(c):
    return map(list, zip(*grid))[c-1]

# returns state at position x,y
def getState(x, y):
    return row(y)[x-1]

# sets state (x,y) to probability: value
def setProb(x, y, value):
    getState(x, y)['val'] = value

#initial grid with no observations
def initGrid():
    #set all non-terminal states to probability = 0.111
    n = len(valid_non_terminal_states)
    val = 1/float(n)
    for s in valid_non_terminal_states:
        x = s['pos'][0]
        y = s['pos'][1]
        setProb(x, y, val)
    setProb(2, 2, 0)
    setProb(4, 2, 0)
    setProb(4, 3, 0)

    return grid

# initial grid in the case which agent knows where it is -- at position x, y
def knownInitGrid(x, y):
    # Make all states 0
    for s in valid_non_terminal_states:
        sx = s['pos'][0]
        sy = s['pos'][1]
        setProb(sx, sy, 0)
    setProb(2, 2, 0)
    setProb(4, 2, 0)
    setProb(4, 3, 0)
    # set known initial state to 1
    setProb(x, y, 1)

    return grid

# returns probability of ending up in state s given action a
def doAction(s, a):
    if a is 'up': return doUp(s)
    if a is 'down': return doDown(s)
    if a is 'left': return doLeft(s)
    if a is 'right': return doRight(s)

# return probability of state s given action is UP
def doUp(s):
    # initial value of s
    val = s['val']
    x = s['pos'][0]
    y = s['pos'][1]

    # list of values acquired from neighbours
    vals = []
    # Agent is in neighbour below and performs UP with a probability of 0.8
    if (y-1) > 0:
        if getState(x, (y-1)) in valid_non_terminal_states:
            vals.append(0.8*getState(x, (y-1))['val'])
    # Agent performs UP with a probability of 0.8 - hits wall
    if s in row(3) or s is s21:
        vals.append(0.8*getState(x, y)['val'])
    # Agent is in neighbour to the right and performs LEFT with a probability of 0.1
    if (x+1) < 5:
        if getState((x+1),y) in valid_non_terminal_states:
            vals.append(0.1*getState((x+1), y)['val'])
    # Agent performs LEFT with a probability of 0.1 - hits wall
    if s in col(1) or s is s32:
        vals.append(0.1*getState(x, y)['val'])
    # Agent is in neighbour to the left and performs RIGHT with a probability of 0.1
    if (x-1) > 0:
        if getState((x-1),y) in valid_non_terminal_states:
            vals.append(0.1*getState((x-1), y)['val'])
    # Agent performs RIGHT with a probability of 0.1 - hits wall
    if s in col(4) or s is s12:
        vals.append(0.1*getState(x, y)['val'])

    # add probabilities
    for v in vals:
        val += v

    return val

# return probability of state s given action is DOWN
def doDown(s):
    # initial value of s
    val = s['val']
    x = s['pos'][0]
    y = s['pos'][1]

    # list of values acquired from neighbours
    vals = []
    # Agent is in neighbour above and performs DOWN with a probability of 0.8
    if (y+1) < 4:
        if getState(x, (y+1)) in valid_non_terminal_states:
            vals.append(0.8*getState(x, (y+1))['val'])
    # Agent performs DOWN with a probability of 0.8 - hits wall
    if s in row(1) or s is s23:
        vals.append(0.8*getState(x, y)['val'])
    # Agent is in neighbour to the right and performs LEFT with a probability of 0.1
    if (x+1) < 5:
        if getState((x+1),y) in valid_non_terminal_states:
            vals.append(0.1*getState((x+1), y)['val'])
    # Agent performs LEFT with a probability of 0.1 - hits wall
    if s in col(1) or s is s32:
        vals.append(0.1*getState(x, y)['val'])
    # Agent is in neighbour to the left and performs RIGHT with a probability of 0.1
    if (x-1) > 0:
        if getState((x-1),y) in valid_non_terminal_states:
            vals.append(0.1*getState((x-1), y)['val'])
    # Agent performs RIGHT with a probability of 0.1 - hits wall
    if s in col(4) or s is s12:
        vals.append(0.1*getState(x, y)['val'])

    # add probabilities
    for v in vals:
        val += v

    return val

# return probability of state s given action is LEFT
def doLeft(s):
    # initial value of s
    val = s['val']
    x = s['pos'][0]
    y = s['pos'][1]

    # list of values acquired from neighbours
    vals = []
    # Agent is in neighbour to the right and performs LEFT with a probability of 0.8
    if (x+1) < 5:
        if getState((x+1),y) in valid_non_terminal_states:
            vals.append(0.8*getState((x+1), y)['val'])
    # Agent performs LEFT with a probability of 0.8 - hits wall
    if s in col(1) or s is s32:
        vals.append(0.8*getState(x, y)['val'])
    # Agent is in neighbour below and performs UP with a probability of 0.1
    if (y-1) > 0:
        if getState(x, (y-1)) in valid_non_terminal_states:
            vals.append(0.1*getState(x, (y-1))['val'])
    # Agent performs UP with a probability of 0.1 - hits wall
    if s in row(3) or s is s21:
        vals.append(0.1*getState(x, y)['val'])
    # Agent is in neighbour above and performs DOWN with a probability of 0.1
    if (y+1) < 4:
        if getState(x,(y+1)) in valid_non_terminal_states:
            vals.append(0.1*getState(x, (y+1))['val'])
    # Agent performs DOWN with a probability of 0.1 - hits wall
    if s in row(1) or s is s23:
        vals.append(0.1*getState(x, y)['val'])

    # add probabilities
    for v in vals:
        val += v

    return val
# return probability of state s given action is RIGHT
def doRight(s):
    # initial value of s
    val = s['val']
    x = s['pos'][0]
    y = s['pos'][1]

    # list of values acquired from neighbours
    vals = []
    # Agent is in neighbour to the left and performs RIGHT with a probability of 0.8
    if (x-1) < 5:
        if getState((x-1),y) in valid_non_terminal_states:
            vals.append(0.8*getState((x-1), y)['val'])
    # Agent performs RIGHT with a probability of 0.8 - hits wall
    if s in col(4) or s is s12:
        vals.append(0.8*getState(x, y)['val'])
    # Agent is in neighbour below and performs UP with a probability of 0.1
    if (y-1) > 0:
        if getState(x, (y-1)) in valid_non_terminal_states:
            vals.append(0.1*getState(x, (y-1))['val'])
    # Agent performs UP with a probability of 0.1 - hits wall
    if s in row(3) or s is s21:
        vals.append(0.1*getState(x, y)['val'])
    # Agent is in neighbour above and performs DOWN with a probability of 0.1
    if (y+1) < 4:
        if getState(x,(y+1)) in valid_non_terminal_states:
            vals.append(0.1*getState(x, (y+1))['val'])
    # Agent performs DOWN with a probability of 0.1 - hits wall
    if s in row(1) or s is s23:
        vals.append(0.1*getState(x, y)['val'])

    # add probabilities
    for v in vals:
        val += v

    return val

# update belief state given action a, and observation o
def update(a, o):
    # values obtained from performing action
    action_vals = updateA(a)
    # multipliers given observation -- to be multiplied with vals_to_update
    observation_values = []
    # cases where observation is 1 or 2
    if type(o) is int:
        # o = 1, 1-wall: (3,1) = 0.9, others = 0.1
        # update_order[5] is (3,1)
        if o == 1:
            for x in range(len(update_order)):
                if x == 9 or x == 10:
                    observation_values.append(0.0)
                elif x == 5:
                    observation_values.append(0.9)
                else:
                    observation_values.append(0.1)
        # o = 2, 2-wall: (3,1) = 0.1, others = 0.1
        # update_order[5] is (3,1)
        if o == 2:
            for x in range(len(update_order)):
                if x == 9 or x == 10:
                    observation_values.append(0)
                elif x == 5:
                    observation_values.append(0.1)
                else:
                    observation_values.append(0.9)
    # case where observation is 'end'
    if o is 'end':
        for x in range(len(update_order)):
                if x == 9 or x == 10:
                    observation_values.append(1)
                else:
                    observation_values.append(0)

    # map action_vals and observation_values to updated_values
    updated_values = []
    for x in range(len(update_order)):
        new_val = action_vals[x] * observation_values[x]
        updated_values.append(new_val)

    #updates grid
    updateVals(updated_values)

# returns values of new belief state given action a
def updateA(a):
    vals_to_update = []
    for s in update_order:
        vals_to_update.append(doAction(s, a))

    updateVals(vals_to_update)
    return vals_to_update

def updateVals(vals):
    index = 0
    for s in update_order:
        s['val'] = vals[index]
        index += 1

# input: initial belief state (ibs), list of actions (la), list of observations (lo)
# output: belief state after performing actions and observations
def beliefStateUpdate(ibs, la, lo):
    iterations = len(la)
    print 'Initial state: '
    printGrid()
    for x in range(iterations):
        update(la[x], lo[x])
        print 'Iteration', x, ', Action:', la[x], ', Observed:', lo[x]
        printGrid()

# Sequences to test:
print 'TEST 1:'
beliefStateUpdate(initGrid(), ['up', 'up', 'up'], [2, 2, 2])

print '\nTEST 2:'
beliefStateUpdate(initGrid(), ['up', 'up', 'up'], [1, 1, 1])

print '\nTEST 3:'
start23 = knownInitGrid(2,3)
beliefStateUpdate(start23, ['right', 'right', 'up'], [1, 1, 'end'])

print '\nTEST 4:'
start11 = knownInitGrid(1,1)
beliefStateUpdate(start11, ['up', 'right', 'right', 'right'], [2, 2, 1, 1])





