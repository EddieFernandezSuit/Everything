
using Acacia_Builder;
using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using System;


public abstract class GameState {
    protected GameStateManager gsm;
    protected SpriteBatch spriteBatch;
    protected Texture2D cursorSpr;

    protected Engine e1 = new Engine();
    protected Game1 game;

    public Entity creature1;

    protected RenderSystem rs;
    protected ClickableSystem cs;
    protected TextSystem ts;

    public ContentManager Content
{
    get;
    private set;
}
    

    protected GameState(Game1 game, GameStateManager gsm){
        this.gsm = gsm;
        this.game = game;
        Content = new ContentManager(game.Services);
        Content.RootDirectory = "Content";
        init();
    }
    public Texture2D loadTexture(string str)
    {
        return Content.Load<Texture2D>(str);
    }
    
    public virtual void init()
    {
        spriteBatch = new SpriteBatch(game.GraphicsDevice);

        rs = new RenderSystem(e1, spriteBatch);
        ts = new TextSystem(e1, spriteBatch);
        cs = new ClickableSystem(e1);
    }
    
    public virtual void update(GameTime gameTime)
    {
        handleInput();
        cs.update(gameTime);

        

    }
    public virtual void draw()
    {
        rs.draw();
        ts.draw();
    }
    public abstract void handleInput();
}