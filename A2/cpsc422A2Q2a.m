function cpsc422A2Q2a ()
    sample = csvread('rs_1.csv');
    [N,D] = size(sample);    
    x = 1:N;
    y = zeros(N,1);
    eLower = zeros(N,1);
    eUpper = ones(N,1);
    numberOfRain = 0;
    currentNumberOfAccepted = 0;
    for i = 1:N;
       if sample(i,1) == 1
           numberOfRain = numberOfRain + 1;
           currentNumberOfAccepted = currentNumberOfAccepted + 1;
       elseif sample (i,1) == 2
           currentNumberOfAccepted = currentNumberOfAccepted + 1;
       end
       y(i,1) = (numberOfRain/currentNumberOfAccepted);
       e = sqrt(log(0.05/2)/(-2*currentNumberOfAccepted));
       lower = y(i,1) - e;
       upper = y(i,1) + e;
       if lower >= 0
           eLower(i,1) = lower;
       end
       if upper <= 1
           eUpper(i,1) = upper;
       end
       
    end
    semilogx(x, eUpper);
    hold on;
    semilogx(x, y);
    hold on;
    semilogx(x, eLower);
    disp('P(r | +w, +s) = ');
    disp(y(N,1));
end

